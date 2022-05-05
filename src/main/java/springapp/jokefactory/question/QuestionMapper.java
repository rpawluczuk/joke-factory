package springapp.jokefactory.question;

import org.mapstruct.Mapper;
import springapp.jokefactory.question.dto.QuestionDto;

@Mapper(componentModel = "spring")
abstract class QuestionMapper {

    abstract QuestionDto mapQuestionToQuestionDto(Question question);
}
