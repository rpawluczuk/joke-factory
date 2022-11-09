package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.TopicCreatorDto;
import springapp.jokefactory.topic.dto.TopicDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

import java.util.Optional;

@Service
public class TopicFacade {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

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

    public Optional<Topic> tryToGetTopicByTopicCreator(TopicCreatorDto topicCreatorDto) {
        if (topicCreatorDto != null && topicCreatorDto.getId() != null) {
            return topicRepository.findById(topicCreatorDto.getId());
        }
        return Optional.empty();
    }

    public TopicDto getTopicDto(Long id) {
        return topicMapper.mapTopicToTopicDto(getTopicById(id), null);
    }

    public TopicItemDto mapTopicToTopicItemDto(Topic topic) {
        return topicMapper.mapTopicToTopicItemDto(topic);
    }

    public TopicDto mapTopicToTopicDto(Topic topic) {
        return topicMapper.mapTopicToTopicDto(topic, null);
    }
}
