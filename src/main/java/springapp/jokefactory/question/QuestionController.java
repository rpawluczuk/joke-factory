package springapp.jokefactory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionCreatorDto;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionListDto;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("http://localhost:4200")
class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/{categoryId}")
    Iterable<QuestionDto> getQuestionByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return questionService.getQuestionByCategoryId(categoryId);
    }

    @PostMapping
    void addQuestion(@RequestBody QuestionCreatorDto questionCreatorDto) {
        questionService.addQuestion(questionCreatorDto);
    }
}