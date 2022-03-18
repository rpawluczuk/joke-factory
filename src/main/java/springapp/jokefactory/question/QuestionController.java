package springapp.jokefactory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionListDto;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("http://localhost:4200")
class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    void addQuestion(@RequestBody QuestionListDto questionListDto) {
        questionService.addQuestion(questionListDto);
    }
}
