package springapp.jokefactory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.dto.QuestionListDto;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;

@Service
class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TopicFacade topicFacade;

    void addQuestion(QuestionListDto questionListDto) {
        Topic category = topicFacade.getTopicById(questionListDto.getCategoryId());
        questionListDto.getQuestions().forEach(questionDto -> {
            Question question = Question.builder()
                    .id(questionDto.getId())
                    .text(questionDto.getText())
                    .category(category)
                    .build();
            questionRepository.save(question);
        });
    }
}
