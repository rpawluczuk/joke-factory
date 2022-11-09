package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicPanelDto {

    private TopicDto initialTopic;
    private List<TopicPackDto> topicPackList;
}
