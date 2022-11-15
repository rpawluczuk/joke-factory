package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.TopicCreatorDto;
import springapp.jokefactory.topic.panel.TopicBlockDto;
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

    private static final PageRequest BASIC_TOPIC_VIEW_PAGE_REQUEST = PageRequest.of(0, 10, Sort.Direction.ASC, "name");

    public Topic getTopicById(Long topicId) {
        if (topicId == 0L) {
            return Topic.getBasicTopic();
        }
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicId));
    }

    public Optional<Topic> tryToGetTopicByTopicItem(TopicItemDto topicItemDto) {
        if (topicItemDto != null && topicItemDto.getValue() != null) {
            return topicRepository.findById(topicItemDto.getValue());
        }
        return Optional.empty();
    }

    public Page<Topic> getConnectedTopicsPage(Topic topicParent, PageRequest pageRequest) {
        if (topicParent.getId() == 0L) {
            return topicRepository.findAll(pageRequest);
        } else {
            return topicRepository.findConnectedTopics(topicParent, pageRequest);
        }
    }

    public Iterable<TopicItemDto> getTopicItemList() {
        return topicRepository.findAll().stream()
                .map(topicMapper::mapTopicToTopicItemDto)
                .collect(Collectors.toList());
    }

    public List<Topic> getConnectedTopicsList(Topic topicParent) {
        return topicRepository.findAllConnectedTopics(topicParent);
    }

    public Page<Topic> getTopicPageByName(String name, PageRequest pageRequest) {
        return topicRepository.findTopicByNameContaining(name, pageRequest);
    }

    public Page<Topic> getConnectedTopicsByCategory(Topic parentTopic, Topic categoryTopic, PageRequest pageRequest) {
        if (categoryTopic.getId() != 0) {
            return topicRepository.findConnectedTopicsByCategory(parentTopic, categoryTopic, pageRequest);
        } else {
            return topicRepository.findConnectedTopics(parentTopic, pageRequest);
        }
    }

    public Optional<Topic> tryToGetTopicByTopicCreator(TopicCreatorDto topicCreatorDto) {
        if (topicCreatorDto != null && topicCreatorDto.getId() != null) {
            return topicRepository.findById(topicCreatorDto.getId());
        }
        return Optional.empty();
    }

    public TopicBlockDto getTopicDto(Long id) {
        return topicMapper.mapTopicToTopicDto(getTopicById(id), null);
    }

    public TopicItemDto mapTopicToTopicItemDto(Topic topic) {
        return topicMapper.mapTopicToTopicItemDto(topic);
    }

    public TopicBlockDto mapTopicToTopicDto(Topic topic) {
        return topicMapper.mapTopicToTopicDto(topic, null);
    }

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
}
