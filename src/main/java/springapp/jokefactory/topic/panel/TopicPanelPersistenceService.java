package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicDto;
import springapp.jokefactory.topic.TopicFacade;

@Service
class TopicPanelPersistenceService {

    @Autowired
    TopicPanelRepository topicPanelRepository;

    @Autowired
    TopicPanelMapper topicPanelMapper;

    @Autowired
    TopicFacade topicFacade;

    TopicBlock addTopic(TopicBlockDto topicBlockDto) {
        Topic topic = topicPanelMapper.mapTopicBlockDtoToTopic(topicBlockDto);
        TopicDto savedTopic = topicFacade.addTopicWithoutParent(topic);
        return topicPanelMapper.mapTopicToTopicBlock(savedTopic);
    }

    void addTopicChild(TopicBlockDto topicBlockDto) {
        TopicDto topicChild = topicPanelMapper.mapTopicBlockDtoToTopicDto(topicBlockDto);
        topicFacade.addTopicChild(topicChild, topicBlockDto.getParentId());
    }
}
