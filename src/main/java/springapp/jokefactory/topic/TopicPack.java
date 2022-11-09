package springapp.jokefactory.topic;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@Scope("prototype")
public class TopicPack {

    private Topic topicParent;
    private Page<Topic> topicPage;
}
