package springapp.jokefactory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.Topic;

import java.util.List;

@Service
public class QuestionFacade {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionRepository questionRepository;

    public QuestionDto mapQuestionToDto(Question question) {
        return questionMapper.mapQuestionToDto(question);
    }

    public QuestionItemDto mapQuestionToItemDto(Question question) {
        return questionMapper.mapQuestionToItemDto(question);
    }

    public List<Question> getQuestionListBySourceCategory(Long sourceCategoryId) {
        return questionRepository.findAllBySourceCategory_Id(sourceCategoryId);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No question found with id: " + id));
    }
}
