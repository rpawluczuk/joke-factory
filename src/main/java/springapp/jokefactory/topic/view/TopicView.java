package springapp.jokefactory.topic.view;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import springapp.jokefactory.topic.Topic;

@Component
@Data
@Scope("singleton")
public class TopicView {

    private Page<Topic> topicPage;
    private boolean categoryFilter;
    private String nameFilter;
}
