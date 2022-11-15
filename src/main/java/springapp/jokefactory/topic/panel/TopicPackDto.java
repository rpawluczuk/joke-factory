package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicPackDto {
    private TopicPageDto topicPage;
    private TopicBlockDto topicParent;
}
