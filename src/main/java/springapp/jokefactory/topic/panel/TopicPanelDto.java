package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicPanelDto {

    private TopicBlockDto initialTopic;
    private List<TopicPackDto> topicPackList;
}
