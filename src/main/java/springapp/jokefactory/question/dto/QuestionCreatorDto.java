package springapp.jokefactory.question.dto;

import lombok.Data;

@Data
public class QuestionCreatorDto {

    private String question;
    private Long categoryId;
}
