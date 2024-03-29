package springapp.jokefactory.topic.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.question.dto.QuestionDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TopicPresenterDto {

    private Long id;
    private String name;
    private boolean isCategory;
    private List<String> children;
    private List<QuestionDto> questions;
    private String dateCreated;
}
