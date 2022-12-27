package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

import java.util.List;

@RestController
@RequestMapping("/api/topics/panel")
@CrossOrigin("http://localhost:3000")
class TopicPanelController {

    @Autowired
    private TopicPanelService topicPanelService;

    @GetMapping(value = "/{initialId}")
    TopicPanelDto getTopicPanel(@PathVariable("initialId") Long initialId) {
        return topicPanelService.initializeTopicPanel(initialId);
    }

    @GetMapping(value = "/get-pack-by-page")
    TopicPackDto getPackByPage(@RequestParam("pageNumber") int pageNumber,
                               @RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getPackByPage(topicPackIndex, pageNumber);
    }

    @GetMapping(value = "/show-children")
    List<TopicPackDto> showChildren(@RequestParam("topicPackIndex") int topicPackIndex,
                                    @RequestParam("parentId") Long parentId) {
        return topicPanelService.showChildren(topicPackIndex, parentId);
    }

    @GetMapping(value = "/random")
    List<TopicPackDto> getRandomTopicResponse(@RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getRandomTopicPack(topicPackIndex);
    }

    @GetMapping(value = "/pack-filter")
    TopicPackDto getFilteredTopicPack(@RequestParam("categoryId") Long categoryId,
                                      @RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getFilteredTopicPack(categoryId, topicPackIndex);
    }

    @GetMapping(value = "/pack-filter/by-question")
    TopicPackDto getFilterPackByQuestion(@RequestParam("questionId") Long questionId,
                                         @RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getFilterPackByQuestion(questionId, topicPackIndex);
    }

    @GetMapping(value = "/question-list")
    Iterable<QuestionItemDto> getQuestionItemList(@RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getQuestionItemList(topicPackIndex);
    }
}
