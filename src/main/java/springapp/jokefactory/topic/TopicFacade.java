package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.topic.view.TopicView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class TopicFacade {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicView topicView;

    @Autowired
    private TopicRelationRepository topicRelationRepository;

    @Autowired
    private TopicCategoryRepository topicCategoryRepository;

    private static final PageRequest BASIC_TOPIC_VIEW_PAGE_REQUEST = PageRequest.of(0, 10, Sort.Direction.ASC, "name");

    public Topic getTopicById(Long topicId) {
        if (topicId == 0L) {
            return Topic.getBasicTopic();
        }
        return findByIdOrThrowException(topicId);
    }

    public TopicDto getTopicDtoById(Long topicId) {
        if (topicId == 0L) {
            return topicMapper.mapTopicToDto(Topic.getBasicTopic());
        }
        Topic topic = findByIdOrThrowException(topicId);
        return topicMapper.mapTopicToDto(topic);
    }

    public Optional<Topic> tryToFindTopicByName(String topicName) {
        return topicRepository.findTopicByName(topicName);
    }


    public Optional<Topic> tryToGetTopicByTopicItem(TopicItemDto topicItemDto) {
        if (topicItemDto != null && topicItemDto.getValue() != null) {
            return topicRepository.findById(topicItemDto.getValue());
        }
        return Optional.empty();
    }

    public Page<TopicDto> getConnectedTopicsPage(Long parentId, PageRequest pageRequest) {
        Page<Topic> topicPage;
        if (parentId == 0L) {
            topicPage = topicRepository.findAll(pageRequest);
        } else {
            Topic topicParent = findByIdOrThrowException(parentId);
            topicPage = topicRepository.findConnectedTopics(topicParent, pageRequest);
        }
        return topicMapper.mapTopicPageToDto(topicPage, pageRequest);
    }

    public Page<TopicDto> getConnectedTopicsPage(Long parentId, Long secondParentId, PageRequest pageRequest) {
        Topic topicParent = findByIdOrThrowException(parentId);
        Topic topicSecondParent = findByIdOrThrowException(secondParentId);
        Page<Topic> topicPage = topicRepository.findConnectedTopicsByTwoParents(topicParent.getId(),
                topicSecondParent.getId(),
                pageRequest);
        return topicMapper.mapTopicPageToDto(topicPage, pageRequest);
    }

    public Page<TopicDto> getConnectedTopicsPage(Long parentId, Long secondParentId, Long categoryId, PageRequest pageRequest) {
        Page<Topic> topicPage;
        if (categoryId != 0) {
            topicPage = topicRepository.findConnectedTopicsByTwoParentsAndCategory(parentId, secondParentId, categoryId, pageRequest);
        } else {
            topicPage = topicRepository.findConnectedTopicsByTwoParents(parentId, secondParentId, pageRequest);
        }
        return topicMapper.mapTopicPageToDto(topicPage, pageRequest);
    }

    public Iterable<TopicItemDto> getTopicItemList() {
        return topicRepository.findAll().stream()
                .map(topicMapper::mapTopicToTopicItemDto)
                .collect(Collectors.toList());
    }

    public List<TopicDto> getConnectedTopicsList(Long parentId) {
        Topic topicParent = findByIdOrThrowException(parentId);
        List<Topic> connectedTopicList = topicRepository.findAllConnectedTopics(topicParent);
        return connectedTopicList.stream()
                .map(topic -> topicMapper.mapTopicToDto(topic))
                .collect(Collectors.toList());
    }

    public Page<Topic> getTopicPageByName(String name, PageRequest pageRequest) {
        return topicRepository.findTopicByNameContaining(name, pageRequest);
    }

    public TopicDto addTopicWithoutParent(Topic topic) {
        Topic savedTopic = topicRepository.save(topic);
        return topicMapper.mapTopicToDto(savedTopic);
    }

    public TopicDto addTopicChild(TopicDto topicChildDto, Long parentId) {
        Topic topicParent = findByIdOrThrowException(parentId);
        Topic topicChild = topicMapper.dtoToTopic(topicChildDto);
        Topic savedTopicChild = tryToFindTopicByName(topicChildDto.getName())
                .orElseGet(() -> topicRepository.save(topicChild));
        topicRelationRepository.save(new TopicRelation(topicParent, savedTopicChild));
        topicRelationRepository.save(new TopicRelation(savedTopicChild, topicParent));
        if (savedTopicChild.isCategory()) {
            topicCategoryRepository.save(new TopicCategory(topicParent, savedTopicChild));
        } else if (topicParent.isCategory()) {
            topicCategoryRepository.save(new TopicCategory(savedTopicChild, topicParent));
        }
        Optional<TopicDto> connectedCategoryDto = topicChildDto.getCategories().stream().findFirst();
        if (connectedCategoryDto.isPresent() && connectedCategoryDto.get().getId() != 0){
            Topic connectedCategory = findByIdOrThrowException(connectedCategoryDto.get().getId());
            topicCategoryRepository.save(new TopicCategory(savedTopicChild, connectedCategory));
            topicRelationRepository.save(new TopicRelation(savedTopicChild, connectedCategory));
            topicRelationRepository.save(new TopicRelation(connectedCategory, savedTopicChild));
        }
        return topicMapper.mapTopicToDto(savedTopicChild);
    }

    public Page<TopicDto> getConnectedTopicsByCategory(Long parentId, Long categoryId, PageRequest pageRequest) {
        Topic parentTopic = findByIdOrThrowException(parentId);
        if (categoryId != 0) {
            Topic categoryTopic = findByIdOrThrowException(categoryId);
            Page<Topic> topicPage = topicRepository.findConnectedTopicsByCategory(parentTopic, categoryTopic, pageRequest);
            return topicMapper.mapTopicPageToDto(topicPage, pageRequest);
        } else {
            Page<Topic> topicPage = topicRepository.findConnectedTopics(parentTopic, pageRequest);
            return topicMapper.mapTopicPageToDto(topicPage, pageRequest);
        }
    }

//    public Optional<Topic> tryToGetTopicByTopicCreator(TopicCreatorDto topicCreatorDto) {
//        if (topicCreatorDto != null && topicCreatorDto.getId() != null) {
//            return topicRepository.findById(topicCreatorDto.getId());
//        }
//        return Optional.empty();
//    }

//    public TopicBlockDto getTopicDto(Long id) {
//        return topicMapper.mapTopicToTopicDto(getTopicById(id), null);
//    }

    public TopicItemDto mapTopicToTopicItemDto(Topic topic) {
        return topicMapper.mapTopicToTopicItemDto(topic);
    }

    public TopicItemDto mapTopicDtoToTopicItemDto(TopicDto topicDto) {
        return topicMapper.mapTopicDtoToTopicItemDto(topicDto);
    }

//    public TopicBlockDto mapTopicToTopicDto(Topic topic) {
//        return topicMapper.mapTopicToTopicDto(topic, null);
//    }

    public void initializeTopicView() {
        topicView.setCategoryFilter(false);
        topicView.setTopicPage(topicRepository.findAll(BASIC_TOPIC_VIEW_PAGE_REQUEST));
    }

    public Iterable<TopicItemDto> getCategoryItemList() {
        TopicItemDto all = new TopicItemDto("All", 0L);
        Set<TopicItemDto> categorySet = new TreeSet<>(new TopicItemComparator());
        categorySet.add(all);
        topicRepository.getAllCategoryTopics().stream()
                .map(topicMapper::mapTopicToTopicItemDto)
                .forEach(categorySet::add);
        return categorySet;
    }

    public Page<Topic> getTopicPage(PageRequest pageRequest) {
        return topicRepository.findAll(pageRequest);
    }

    public Page<Topic> getAllCategoryTopicsPage(PageRequest pageRequest) {
        return topicRepository.getAllCategoryTopicsPage(pageRequest);
    }

    public void deleteTopic(Long id) {
        Topic topicToDelete = findByIdOrThrowException(id);
        topicRelationRepository.deleteAll(topicRelationRepository.findAllTopicRelations(topicToDelete.getId()));
        topicCategoryRepository.deleteTopicCategoriesByTopic_Id(topicToDelete.getId());
        topicRepository.delete(topicToDelete);
    }

    public void deleteTopicRelation(Long topicParentId, Long topicChildId){
        TopicRelation topicRelation = topicRelationRepository
                .findTopicRelationByParentIdAndChildId(topicParentId, topicChildId)
                .orElseThrow(() -> new IllegalArgumentException("No topic relation found with parent id: "
                        + topicParentId + " and child id: " + topicChildId));
        topicRelationRepository.delete(topicRelation);
        topicCategoryRepository
                .findTopicCategoryByParentIdAndChildId(topicParentId, topicChildId)
                .ifPresent(topicCategory -> topicCategoryRepository.delete(topicCategory));
    }

    private Topic findByIdOrThrowException(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicId));
    }
}
