package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RandomTopicResponseDto {

    private TopicCreatorDto randomTopic;
    private int randomPage;
    private List<TopicCreatorDto> topicCreatorChildList;
}
