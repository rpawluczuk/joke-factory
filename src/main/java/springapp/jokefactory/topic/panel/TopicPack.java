package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.topic.Topic;

@Component
@Data
@Builder
@Scope("prototype")
public class TopicPack {

    private Topic topicParent;
    private Page<Topic> topicPage;
    private Topic categoryFilter;
    private Question questionFilter;
}
