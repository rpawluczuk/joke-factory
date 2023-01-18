package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springapp.jokefactory.topic.TopicDto;

@Component
@Data
@Builder
@Scope("prototype")
public class TopicBlock {

    private TopicDto topic;
    private boolean isSecondParent;
    private boolean isSelected;
    private Integer topicPackIndex;
    private Long parentId;
}
