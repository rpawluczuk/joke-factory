package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicDto;
import springapp.jokefactory.topic.TopicFacade;

@Service
class TopicPanelPersistenceService {

    @Autowired
    TopicPanelMapper topicPanelMapper;

    @Autowired
    TopicFacade topicFacade;


    void addTopicChild(TopicBlockDto topicBlockDto) {
        TopicDto topicChild = topicPanelMapper.mapTopicBlockDtoToTopicDto(topicBlockDto);
        topicFacade.addTopicChild(topicChild, topicBlockDto.getParentId());
        if (topicBlockDto.getSecondParentId() != null){
            topicFacade.addTopicChild(topicChild, topicBlockDto.getSecondParentId());
        }
    }

    void deleteTopicRelation(Long topicParentId, Long topicChildId) {
        topicFacade.deleteTopicRelation(topicParentId, topicChildId);
        topicFacade.deleteTopicRelation(topicChildId, topicParentId);
    }
}
