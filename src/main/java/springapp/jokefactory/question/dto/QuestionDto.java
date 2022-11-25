package springapp.jokefactory.question.dto;

import lombok.Data;

@Data
public class QuestionDto {

    private Long sourceCategoryId;
    private String questionText;
    private Long targetCategoryId;
}
