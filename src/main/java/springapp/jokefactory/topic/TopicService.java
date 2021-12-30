package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.topic.dto.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
class TopicService {

    @Autowired
    private TopicRepository topicRepository;

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
        return topicRepository.getAllCategoryTopics().stream()
                .map(topicMapper::mapTopicToTopicItemDto)
                .collect(Collectors.toList());
    }

    Iterable<TopicCreatorChildDto> getTopicCreatorChildList(Long parentId) {
        Topic topic = topicRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + parentId));
        return topicRepository.findAllConnectedTopics(topic)
                .stream().map(connectedTopic -> topicMapper.mapTopicToTopicCreatorChildDto(connectedTopic, parentId))
                .collect(Collectors.toList());
    }

    TopicCreatorChildRowResponseDto getTopicCreatorChildRowAndPage(TopicCreatorChildRowRequestDto topicCreatorChildRowRequest) {
        TopicPaginationDto topicPagination = topicCreatorChildRowRequest.getTopicPagination();
        Long parentId = topicCreatorChildRowRequest.getParentId();
        PageRequest pageRequest = PageRequest.of(topicPagination.getCurrentPage(), topicPagination.getPageSize());
        Topic topic = topicRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + parentId));
        Page<Topic> topicPage = topicRepository.findConnectedTopics(topic, pageRequest);
        List<TopicCreatorChildDto> topicCreatorChildList = topicPage.getContent().stream()
                .map(connectedTopic -> topicMapper.mapTopicToTopicCreatorChildDto(connectedTopic, parentId))
                .collect(Collectors.toList());
        topicPagination.setTotalItems(topicPage.getTotalElements());
        topicPagination.setTotalPages(topicPage.getTotalPages());
        return TopicCreatorChildRowResponseDto.builder()
                .topicCreatorChildList(topicCreatorChildList)
                .parentId(parentId)
                .topicPagination(topicPagination)
                .build();
    }

    TopicCreatorChildRowResponseDto getAllTopicCreatorChildPage(TopicCreatorChildRowRequestDto topicCreatorChildRowRequest) {
        TopicPaginationDto topicPagination = topicCreatorChildRowRequest.getTopicPagination();
        PageRequest pageRequest = PageRequest.of(topicPagination.getCurrentPage(), topicPagination.getPageSize(), Sort.Direction.ASC, "name");
        Page<Topic> topicPage = topicRepository.findAll(pageRequest);
        List<TopicCreatorChildDto> topicCreatorChildList = topicPage.getContent().stream()
                .map(connectedTopic -> topicMapper.mapTopicToTopicCreatorChildDto(connectedTopic, null))
                .collect(Collectors.toList());
        topicPagination.setTotalItems(topicPage.getTotalElements());
        topicPagination.setTotalPages(topicPage.getTotalPages());
        return TopicCreatorChildRowResponseDto.builder()
                .topicCreatorChildList(topicCreatorChildList)
                .parentId(null)
                .topicPagination(topicPagination)
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
        List<TopicCreatorChildDto> topicCreatorChildList = topicPage.getContent().stream()
                .map(connectedTopic -> topicMapper.mapTopicToTopicCreatorChildDto(connectedTopic, null))
                .collect(Collectors.toList());
        return RandomTopicIdResponseDto.builder()
                .randomTopicId(randomTopicId)
                .randomPage(randomPage)
                .topicCreatorChildList(topicCreatorChildList)
                .build();
    }

    void addTopic(TopicCreatorDto topicCreatorDTO) {
        Topic topic = topicRepository.findTopicByName(topicCreatorDTO.getName())
                .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorDtoToTopic(topicCreatorDTO)));
        topicCreatorDTO.getChildren().forEach(topicCreatorDTOChild -> {
            if (!topicCreatorDTOChild.getName().isEmpty()) {
                Topic topicChild = topicRepository.findTopicByName(topicCreatorDTOChild.getName())
                        .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorChildDtoToTopic(topicCreatorDTOChild)));
                topicRelationRepository.save(new TopicRelation(topic, topicChild));
            }
        });
    }

    void addTopicChild(TopicCreatorChildDto topicCreatorChildDto) {
        Topic topicParent = topicRepository.findById(topicCreatorChildDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicCreatorChildDto.getParentId()));
        Topic topicChild = topicRepository.findTopicByName(topicCreatorChildDto.getName())
                .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorChildDtoToTopic(topicCreatorChildDto)));
        topicRelationRepository.save(new TopicRelation(topicParent, topicChild));
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
        topicToEdit.getTopicsOfCategory().forEach(topic -> {
            topic.setCategories(Collections.emptyList());
            topicRepository.save(topic);
        });
        if (topicToEdit.isCategory()) {
            List<Topic> topicsOfCategory = topicRepository.findAllConnectedTopics(topicToEdit);
            topicsOfCategory.forEach(topic -> {
                List<Topic> categories = topic.getCategories();
                categories.add(topicToEdit);
                topic.setCategories(categories);
                topicRepository.save(topic);
            });
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
        topicRelationRepository.findAllTopicRelationsConnectedWithTopic(topicToDelete.getId())
                .forEach(topicRelationRepository::delete);
        topicRepository.delete(topicToDelete);
    }

    void deleteTopicRelation(Long topicParentId, Long topicChildId) {
        TopicRelation topicRelation = topicRelationRepository
                .findTopicRelationByParentIdAndChildId(topicParentId, topicChildId)
                .orElseThrow(() -> new IllegalArgumentException("No topic relation found with parent id: "
                        + topicParentId + " and child id: " + topicChildId));
        topicRelationRepository.delete(topicRelation);
    }
}
