package springapp.jokefactory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("http://localhost:3000")
class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "source-category/{categoryId}")
    Iterable<QuestionDto> getQuestionByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return questionService.getQuestionDtoByCategoryId(categoryId);
    }

    @GetMapping(value = "/{id}")
    QuestionDto getQuestionById(@PathVariable("id") Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping
    Iterable<QuestionDto> addQuestion(@RequestBody QuestionDto questionDto) {
        return questionService.addQuestion(
                questionDto.getSourceCategory().getValue(),
                questionDto.getQuestionText(),
                questionDto.getTargetCategory().getValue());
    }

    @PutMapping
    Iterable<QuestionDto> editQuestion(@RequestBody QuestionDto questionDto) {
        return questionService.editQuestion(questionDto);
    }

    @DeleteMapping(value = "/{id}")
    Iterable<QuestionDto> deleteQuestionById(@PathVariable("id") Long id) {
        return questionService.deleteQuestionById(id);
    }
}
