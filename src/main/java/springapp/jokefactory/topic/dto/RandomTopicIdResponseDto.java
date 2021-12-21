package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RandomTopicIdResponseDto {

    private Long randomTopicId;
    private int randomPage;
    private List<TopicCreatorChildDto> topicCreatorChildList;
}
