package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.topic.dto.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicCategoryRepository topicCategoryRepository;

    @Autowired
    private JokeFacade jokeFacade;

    @Autowired
    private TopicRelationRepository topicRelationRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private TopicPaginationDto topicPaginationDto;

    TopicCreatorDto getTopicCreator(Long id) {
        return topicFacade.tryToGetTopicCreator(id).orElse(null);
    }

    Iterable<TopicPresenterDto> getTopicPresenterList() {
        PageRequest pageRequest = PageRequest.of(topicPaginationDto.getCurrentPage(), topicPaginationDto.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Topic> topicPage = topicRepository.findAll(pageRequest);
        topicPaginationDto.setTotalPages(topicPage.getTotalPages());
        topicPaginationDto.setTotalItems(topicPage.getTotalElements());
        return topicPage.getContent().stream()
                .map(topic -> {
                    List<Topic> connectedTopicList = topicRepository.findAllConnectedTopics(topic);
                    return topicMapper.mapTopicToTopicPresenterDto(topic, connectedTopicList);
                }).collect(Collectors.toList());
    }

    public Iterable<TopicPresenterDto> getTopicPresenterListByName(String name) {
        PageRequest pageRequest = PageRequest.of(topicPaginationDto.getCurrentPage(), topicPaginationDto.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Topic> topicPage = topicRepository.findTopicByNameContaining(name, pageRequest);
        topicPaginationDto.setTotalPages(topicPage.getTotalPages());
        topicPaginationDto.setTotalItems(topicPage.getTotalElements());
        return topicPage.getContent().stream().map(topic -> {
            List<Topic> connectedTopicList = topicRepository.findAllConnectedTopics(topic);
            return topicMapper.mapTopicToTopicPresenterDto(topic, connectedTopicList);
        }).collect(Collectors.toList());
    }

    Iterable<TopicItemDto> getTopicItemList() {
        return topicRepository.findAll().stream()
                .map(topicMapper::mapTopicToTopicItemDto)
                .collect(Collectors.toList());
    }


    public Iterable<TopicItemDto> getCategoryList() {
        TopicItemDto all = new TopicItemDto("All");
        Set<TopicItemDto> categorySet = new TreeSet<>(new TopicItemComparator());
        categorySet.add(all);
        topicRepository.getAllCategoryTopics().stream()
                .map(topicMapper::mapTopicToTopicItemDto)
                .forEach(categorySet::add);
        return categorySet;
    }

    Iterable<TopicCreatorDto> getTopicCreatorChildList(Long parentId) {
        Topic topic = topicRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + parentId));
        return topicRepository.findAllConnectedTopics(topic)
                .stream().map(connectedTopic -> topicMapper.mapTopicToTopicCreatorDto(connectedTopic, parentId))
                .collect(Collectors.toList());
    }

    TopicCreatorChildRowResponseDto getTopicCreatorChildRowAndPage(TopicCreatorChildRowRequestDto topicCreatorChildRowRequest) {
        TopicPackPaginationDto topicPackPagination = topicCreatorChildRowRequest.getTopicPackPagination();
        Long parentId = topicCreatorChildRowRequest.getParentId();
        PageRequest pageRequest = PageRequest.of(topicPackPagination.getCurrentPage(), topicPackPagination.getPageSize());
        Topic topic = topicRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + parentId));
        Page<Topic> topicPage = topicRepository.findConnectedTopics(topic, pageRequest);
        List<TopicCreatorDto> topicCreatorChildList = topicPage.getContent().stream()
                .map(connectedTopic -> topicMapper.mapTopicToTopicCreatorDto(connectedTopic, parentId))
                .collect(Collectors.toList());
        topicPackPagination.setTotalItems(topicPage.getTotalElements());
        topicPackPagination.setTotalPages(topicPage.getTotalPages());
        return TopicCreatorChildRowResponseDto.builder()
                .topicCreatorChildList(topicCreatorChildList)
                .parentId(parentId)
                .topicPackPagination(topicPackPagination)
                .build();
    }

    TopicCreatorChildRowResponseDto getAllTopicCreatorChildPage(TopicCreatorChildRowRequestDto topicCreatorChildRowRequest) {
        TopicPackPaginationDto topicPackPagination = topicCreatorChildRowRequest.getTopicPackPagination();
        PageRequest pageRequest = PageRequest.of(topicPackPagination.getCurrentPage(), topicPackPagination.getPageSize(), Sort.Direction.ASC, "name");
        Page<Topic> topicPage = topicRepository.findAll(pageRequest);
        List<TopicCreatorDto> topicCreatorChildList = topicPage.getContent().stream()
                .map(connectedTopic -> topicMapper.mapTopicToTopicCreatorDto(connectedTopic,null))
                .collect(Collectors.toList());
        topicPackPagination.setTotalItems(topicPage.getTotalElements());
        topicPackPagination.setTotalPages(topicPage.getTotalPages());
        return TopicCreatorChildRowResponseDto.builder()
                .topicCreatorChildList(topicCreatorChildList)
                .parentId(null)
                .topicPackPagination(topicPackPagination)
                .build();
    }

    TopicPaginationDto getTopicPagination() {
        return topicPaginationDto;
    }

    RandomTopicIdResponseDto getRandomTopicResponse(RandomTopicIdRequestDto request) {
        Random rand = new Random();
        int randomPage = rand.nextInt(request.getTotalPages());
        PageRequest pageRequest = PageRequest.of(randomPage, request.getPageSize(), Sort.Direction.ASC, "name");
        Page<Topic> topicPage;
        if (request.getParentId() != null) {
            Topic topic = topicRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + request.getParentId()));
            topicPage = topicRepository.findConnectedTopics(topic, pageRequest);
        } else {
            topicPage = topicRepository.findAll(pageRequest);
        }
        Long randomTopicId = topicPage.getContent().get(rand.nextInt(topicPage.getContent().size())).getId();
        List<TopicCreatorDto> topicCreatorChildList = topicPage.getContent().stream()
                .map(connectedTopic -> topicMapper.mapTopicToTopicCreatorDto(connectedTopic,null))
                .collect(Collectors.toList());
        return RandomTopicIdResponseDto.builder()
                .randomTopicId(randomTopicId)
                .randomPage(randomPage)
                .topicCreatorChildList(topicCreatorChildList)
                .build();
    }

    TopicCreatorDto addTopic(TopicCreatorDto topicCreatorDto) {
        Topic topic = topicRepository.save(topicMapper.mapTopicCreatorDtoToTopic(topicCreatorDto));
        return topicMapper.mapTopicToTopicCreatorDto(topic, null);
    }

    TopicCreatorDto addTopicChild(TopicCreatorDto topicCreatorDto) {
        Topic topicParent = topicRepository.findById(topicCreatorDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicCreatorDto.getParentId()));
        Topic topicChild = topicRepository.findTopicByName(topicCreatorDto.getName())
                .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorDtoToTopic(topicCreatorDto)));
        topicRelationRepository.save(new TopicRelation(topicParent, topicChild));
        if (topicChild.isCategory()) {
            topicCategoryRepository.save(new TopicCategory(topicParent, topicChild));
        } else if (topicParent.isCategory()) {
            topicCategoryRepository.save(new TopicCategory(topicChild, topicParent));
        }
        return topicMapper.mapTopicToTopicCreatorDto(topicChild, topicCreatorDto.getParentId());
    }

    void editTopicName(TopicCreatorDto topicCreatorDTO) {
        Topic topicToEdit = topicRepository.findById(topicCreatorDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicCreatorDTO.getId()));
        topicToEdit.setName(topicCreatorDTO.getName());
        topicRepository.save(topicToEdit);
    }

    void changeCategoryStatus(Long id) {
        Topic topicToEdit = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + id));
        topicToEdit.setCategory(!topicToEdit.isCategory());
        if (topicToEdit.isCategory()){
            topicRepository.findAllConnectedTopics(topicToEdit)
                    .forEach(connectedTopic -> topicCategoryRepository.save(new TopicCategory(connectedTopic, topicToEdit)));
        } else {
            topicCategoryRepository.deleteTopicCategoriesByCategory_Id(topicToEdit.getId());
        }
        topicRepository.save(topicToEdit);
    }

    void updateTopicPagination(TopicPaginationDto topicPaginationDto) {
        this.topicPaginationDto.setCurrentPage(topicPaginationDto.getCurrentPage());
        this.topicPaginationDto.setTotalItems(topicPaginationDto.getTotalItems());
        this.topicPaginationDto.setTotalPages(topicPaginationDto.getTotalPages());
        this.topicPaginationDto.setPageSize(topicPaginationDto.getPageSize());
    }

    void deleteTopic(Long id) {
        Topic topicToDelete = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + id));
        topicRelationRepository.deleteAll(topicRelationRepository.findAllTopicRelations(topicToDelete.getId()));
        topicCategoryRepository.deleteTopicCategoriesByTopic_Id(topicToDelete.getId());
        topicRepository.delete(topicToDelete);
    }

    void deleteTopicRelation(Long topicParentId, Long topicChildId) {
        TopicRelation topicRelation = topicRelationRepository
                .findTopicRelationByParentIdAndChildId(topicParentId, topicChildId)
                .orElseThrow(() -> new IllegalArgumentException("No topic relation found with parent id: "
                        + topicParentId + " and child id: " + topicChildId));
        topicRelationRepository.delete(topicRelation);
        topicCategoryRepository
                .findTopicCategoryByParentIdAndChildId(topicParentId, topicChildId)
                .ifPresent(topicCategory -> topicCategoryRepository.delete(topicCategory));
    }
}
