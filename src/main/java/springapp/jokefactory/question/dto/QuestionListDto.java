package springapp.jokefactory.question.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionListDto {

    private Long categoryId;
    private List<QuestionItemDto> questions;
}
