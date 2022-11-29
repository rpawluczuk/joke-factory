package springapp.jokefactory.question;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.view.TopicViewFacade;

@Service
class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private TopicViewFacade topicViewFacade;


    Iterable<QuestionDto> addQuestion(Long sourceCategoryId, String questionText, Long targetCategoryId) {
        Topic sourceCategory = topicFacade.getTopicById(sourceCategoryId);
        Topic targetCategory = topicFacade.getTopicById(targetCategoryId);
        Question question = Question.builder()
                .question(questionText)
                .sourceCategory(sourceCategory)
                .targetCategory(targetCategory)
                .build();
        questionRepository.save(question);
        topicViewFacade.refreshTopicView();
        return getQuestionDtoByCategoryId(sourceCategoryId);
    }

    Iterable<QuestionDto> editQuestion(QuestionDto questionDto) {
        Topic sourceCategory = topicFacade.getTopicById(questionDto.getSourceCategory().getValue());
        Topic targetCategory = topicFacade.getTopicById(questionDto.getTargetCategory().getValue());
        Question question = Question.builder()
                .id(questionDto.getId())
                .question(questionDto.getQuestionText())
                .sourceCategory(sourceCategory)
                .targetCategory(targetCategory)
                .build();
        questionRepository.save(question);
        topicViewFacade.refreshTopicView();
        return getQuestionDtoByCategoryId(questionDto.getSourceCategory().getValue());
    }

    public Iterable<QuestionDto> getQuestionDtoByCategoryId(Long categoryId) {
        return questionRepository.findAllBySourceCategory_Id(categoryId).stream()
                .map(questionMapper::mapQuestionToDto)
                .collect(Collectors.toList());
    }

    QuestionDto getQuestionById(Long id) {
        Question questionToEdit = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No question found with id: " + id));
        return questionMapper.mapQuestionToDto(questionToEdit);
    }

    Iterable<QuestionDto> deleteQuestionById(Long id) {
        Question questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No question found with id: " + id));
        questionRepository.delete(questionToDelete);
        topicViewFacade.refreshTopicView();
        return getQuestionDtoByCategoryId(questionToDelete.getSourceCategory().getId());
    }
}
