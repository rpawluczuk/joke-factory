package springapp.jokefactory.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionItemDto {

    private Long value;
    private String label;
}
