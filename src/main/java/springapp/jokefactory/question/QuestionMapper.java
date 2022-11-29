package springapp.jokefactory.question;

import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.TopicFacade;

import java.util.Optional;

@Service
class QuestionMapper {

    @Autowired
    private TopicFacade topicFacade;

    QuestionItemDto mapQuestionToItemDto(Question question) {
        return new QuestionItemDto(question.getId(), question.getQuestion());
    }

    QuestionDto mapQuestionToDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .sourceCategory(topicFacade.mapTopicToTopicItemDto(question.getSourceCategory()))
                .questionText(question.getQuestion())
                .targetCategory(topicFacade.mapTopicToTopicItemDto(question.getTargetCategory()))
                .build();
    }
}
