package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
@Builder
public class TopicPackDto {
    private TopicPageDto topicBlockPage;
    private TopicBlockDto topicBlockParent;
    private TopicItemDto categoryFilter;
    private QuestionItemDto questionFilter;
    private boolean isAnySelection;
    private int topicPackIndex;
}
