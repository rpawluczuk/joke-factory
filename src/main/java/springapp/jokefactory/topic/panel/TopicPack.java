package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.topic.TopicDto;

@Component
@Data
@Builder
@Scope("prototype")
public class TopicPack {

    private TopicBlock topicBlockParent;
    private Page<TopicBlock> topicBlockPage;
    private TopicDto categoryFilter;
    private Question questionFilter;
    private boolean isAnySelection;
    private int topicPackIndex;
}
