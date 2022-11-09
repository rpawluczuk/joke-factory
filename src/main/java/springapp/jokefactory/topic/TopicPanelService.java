package springapp.jokefactory.topic;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.*;

import java.util.List;
import java.util.Random;

@Service
public class TopicPanelService {

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicPanel topicPanel;

    private final TopicPanelMapper topicPanelMapper = Mappers.getMapper(TopicPanelMapper.class);

    private static final Random RANDOM = new Random();
    private static final PageRequest BASIC_PAGE_REQUEST = PageRequest.of(0, 20, Sort.Direction.ASC, "name");

    TopicPanelDto initializeTopicPanel(Long initialId) {
        topicPanel.clearPanel();
        TopicPack topicPack = getTopicPack(initialId, BASIC_PAGE_REQUEST);
        topicPanel.addTopicPack(topicPack);
        return topicPanelMapper.mapTopicPanelToDto(topicPanel);
    }

    TopicPack getTopicPack(Long parentId, PageRequest pageRequest) {
        Topic topicParent = topicFacade.getTopicById(parentId);
        Page<Topic> topicPage;
        if (parentId == 0L) {
            topicPage = topicRepository.findAll(pageRequest);
        } else {
            topicPage = topicRepository.findConnectedTopics(topicParent, pageRequest);
        }
        return TopicPack.builder()
                .topicParent(topicParent)
                .topicPage(topicPage)
                .build();
    }

    TopicPackDto getPackByPage(int topicPackIndex, int pageNumber) {
        Long parentId = topicPanel.getTopicPackList().get(topicPackIndex).getTopicParent().getId();
        PageRequest pageRequest = PageRequest.of(pageNumber, 20, Sort.Direction.ASC, "name");
        TopicPack topicPack = getTopicPack(parentId, pageRequest);
        topicPanel.changeTopicPage(topicPackIndex, topicPack.getTopicPage());
        return topicPanelMapper.mapTopicPackToDto(topicPack);
    }

    List<TopicPackDto> showChildren(int topicPackIndex, Long parentId) {
        topicPanel.changeSelectedTopic(topicPackIndex, parentId);
        TopicPack topicPackChildren = getTopicPack(parentId, BASIC_PAGE_REQUEST);
        topicPanel.addTopicPack(topicPackChildren, topicPackIndex);
        topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex + 1));
        return List.of(
                topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex)),
                topicPanelMapper.mapTopicPackToDto(topicPackChildren));
    }

    List<TopicPackDto> getRandomTopicPack(int topicPackIndex) {
        Page<Topic> topicPage = topicPanel.getTopicPage(topicPackIndex);
        int randomPageNumber = RANDOM.nextInt(topicPage.getTotalPages());
        PageRequest pageRequest = PageRequest.of(randomPageNumber, topicPage.getSize(), Sort.Direction.ASC, "name");
        TopicPack topicPack = getTopicPack(topicPanel.getTopicParent(topicPackIndex).getId(), pageRequest);
        int randomIndex = RANDOM.nextInt(topicPack.getTopicPage().getContent().size());
        Long randomTopicId = topicPack.getTopicPage().getContent().get(randomIndex).getId();
        TopicPack topicPackChildren = getTopicPack(randomTopicId, BASIC_PAGE_REQUEST);
        topicPanel.addTopicPack(topicPackChildren, topicPackIndex);
        topicPanel.changeTopicPage(topicPackIndex, topicPack.getTopicPage());
        return List.of(
                topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex)),
                topicPanelMapper.mapTopicPackToDto(topicPackChildren));
    }

    TopicPackDto getFilteredTopicPack(Long categoryId, int topicPackIndex) {
        Topic parentTopic = topicPanel.getTopicParent(topicPackIndex);
        Page<Topic> topicPage;
        if (categoryId != 0) {
            Topic categoryTopic = topicFacade.getTopicById(categoryId);
            topicPage = topicRepository.findConnectedTopicsByCategory(parentTopic, categoryTopic, BASIC_PAGE_REQUEST);
        } else {
            topicPage = topicRepository.findConnectedTopics(parentTopic, BASIC_PAGE_REQUEST);
        }
        return topicPanelMapper.mapTopicPackToDto(topicPanel.changeTopicPage(topicPackIndex, topicPage));
    }
}
