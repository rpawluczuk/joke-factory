package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicPackDto {
    private TopicPageDto topicPage;
    private TopicDto topicParent;
}
