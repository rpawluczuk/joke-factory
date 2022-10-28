package springapp.jokefactory.topic;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.Joke;
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

    private static final Random RANDOM = new Random();


    Iterable<TopicCreatorDto> getFilteredTopicPack(Long categoryId, int pageSize, Long parentId) {
        PageRequest pageRequest = PageRequest.of(0, pageSize,
                Sort.Direction.DESC, "name");
        Topic parentTopic = topicFacade.getTopicById(parentId);
        Topic categoryTopic = topicFacade.getTopicById(categoryId);
        Page<Topic> pageTopics = topicRepository.findConnectedTopicsByCategory(parentTopic, categoryTopic, pageRequest);
        return pageTopics.getContent().stream()
                .map(topic -> topicMapper.mapTopicToTopicCreatorDto(topic, parentId))
                .collect(Collectors.toList());
    }

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
        TopicItemDto all = new TopicItemDto("All", 0L);
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

    RandomTopicResponseDto getRandomTopicResponse(Long parentId, int totalPages, int pageSize) {
        int randomPage = RANDOM.nextInt(totalPages);
        PageRequest pageRequest = PageRequest.of(RANDOM.nextInt(totalPages), pageSize, Sort.Direction.ASC, "name");
        Page<Topic> topicPage;
        if (parentId != null) {
            Topic topic = topicFacade.getTopicById(parentId);
            topicPage = topicRepository.findConnectedTopics(topic, pageRequest);
        } else {
            topicPage = topicRepository.findAll(pageRequest);
        }
        Topic topic = topicPage.getContent().get(RANDOM.nextInt(topicPage.getContent().size()));

        List<TopicCreatorDto> topicCreatorChildList = topicPage.getContent().stream()
                .map(connectedTopic -> topicMapper.mapTopicToTopicCreatorDto(connectedTopic,null))
                .collect(Collectors.toList());

        TopicCreatorDto randomTopic = topicMapper.mapTopicToTopicCreatorDto(topic, parentId);
        return RandomTopicResponseDto.builder()
                .randomTopic(randomTopic)
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
