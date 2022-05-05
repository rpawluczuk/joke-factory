package springapp.jokefactory.question;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.dto.QuestionCreatorDto;
import springapp.jokefactory.question.dto.QuestionDto;
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


    void addQuestion(QuestionCreatorDto questionCreatorDto) {
        Topic category = topicFacade.getTopicById(questionCreatorDto.getCategoryId());
        Question question = Question.builder()
            .question(questionCreatorDto.getQuestion())
            .category(category)
            .build();
        questionRepository.save(question);
    }

    public Iterable<QuestionDto> getQuestionByCategoryId(Long categoryId) {
        return questionRepository.findAllByCategory_Id(categoryId).stream()
            .map(questionMapper::mapQuestionToQuestionDto)
            .collect(Collectors.toList());
    }
}
