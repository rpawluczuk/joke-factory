package springapp.jokefactory.question;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;

@Service
class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private TopicFacade topicFacade;


    Iterable<QuestionItemDto> addQuestion(Long sourceCategoryId, String questionText, Long targetCategoryId) {
        Topic sourceCategory = topicFacade.getTopicById(sourceCategoryId);
        Topic targetCategory = topicFacade.getTopicById(targetCategoryId);
        Question question = Question.builder()
                .question(questionText)
                .sourceCategory(sourceCategory)
                .targetCategory(targetCategory)
                .build();
        questionRepository.save(question);
        return getQuestionByCategoryId(sourceCategoryId);
    }

    public Iterable<QuestionItemDto> getQuestionByCategoryId(Long categoryId) {
        return questionRepository.findAllBySourceCategory_Id(categoryId).stream()
                .map(questionMapper::mapQuestionToItemDto)
                .collect(Collectors.toList());
    }

    Iterable<QuestionItemDto> deleteQuestionById(Long id) {
        Question questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No question found with id: " + id));
        questionRepository.delete(questionToDelete);
        return getQuestionByCategoryId(questionToDelete.getSourceCategory().getId());
    }
}
