package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
@Builder
public class TopicPackDto {
    private TopicPageDto topicPage;
    private TopicBlockDto topicParent;
    private TopicItemDto categoryFilter;
    private QuestionItemDto questionFilter;
}
