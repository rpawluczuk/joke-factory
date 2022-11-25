package springapp.jokefactory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionItemDto;

import java.util.Map;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("http://localhost:3000")
class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/{categoryId}")
    Iterable<QuestionItemDto> getQuestionByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return questionService.getQuestionByCategoryId(categoryId);
    }

//    @PostMapping
//    Iterable<QuestionItemDto> addQuestion(
//            @RequestParam("sourceCategoryId") Long sourceCategoryId,
//            @RequestParam("questionText") String questionText,
//            @RequestParam("targetCategoryId") Long targetCategoryId) {
//        return questionService.addQuestion(sourceCategoryId, questionText, targetCategoryId);
//    }

    @PostMapping
    Iterable<QuestionItemDto> addQuestion(@RequestBody QuestionDto questionDto) {
        return questionService.addQuestion(
                questionDto.getSourceCategoryId(),
                questionDto.getQuestionText(),
                questionDto.getTargetCategoryId());
    }

    @DeleteMapping(value = "/{id}")
    Iterable<QuestionItemDto> deleteQuestionById(@PathVariable("id") Long id) {
        return questionService.deleteQuestionById(id);
    }
}
