package springapp.jokefactory.question;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.question.dto.QuestionItemDto;

@Mapper(componentModel = "spring")
abstract class QuestionMapper {

    @Mapping(target = "label", source = "question")
    @Mapping(target = "value", source = "id")
    abstract QuestionItemDto mapQuestionToItemDto(Question question);
}
