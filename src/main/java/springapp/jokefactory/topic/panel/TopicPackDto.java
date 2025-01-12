package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
@Builder
public class TopicPackDto {
    private TopicBlockDto topicBlockParent;
    private TopicBlockDto topicBlockSecondParent;
    private Page<TopicBlockDto> topicBlockPage;
    private TopicItemDto categoryFilter;
    private QuestionItemDto questionFilter;
    private boolean isAnySelection;
    private Long selectedId;
    private Integer topicPackIndex;
}
