package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.TopicItemDto;

import java.util.Optional;

@Service
public class TopicFacade {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    public Optional<Topic> tryToGetTopicByTopicItem(TopicItemDto topicItemDto) {
        if (topicItemDto != null && topicItemDto.getId() != null) {
            return topicRepository.findById(topicItemDto.getId());
        }
        return Optional.empty();
    }

    public TopicItemDto mapTopicToTopicItemDto(Topic topic) {
        return topicMapper.mapTopicToTopicItemDto(topic);
    }
}
