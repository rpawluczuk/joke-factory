package springapp.jokefactory.question.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
@Builder
public class QuestionDto {

    private Long id;
    private TopicItemDto sourceCategory;
    private String questionText;
    private TopicItemDto targetCategory;
}
