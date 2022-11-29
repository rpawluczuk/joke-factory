package springapp.jokefactory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionItemDto;

@Service
public class QuestionFacade {

    @Autowired
    private QuestionMapper questionMapper;

    public QuestionDto mapQuestionToDto(Question question) {
        return questionMapper.mapQuestionToDto(question);
    }
}
