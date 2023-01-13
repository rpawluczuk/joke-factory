package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics/panel")
@CrossOrigin("http://localhost:3000")
class TopicPanelController {

    @Autowired
    private TopicPanelService topicPanelService;

    @Autowired
    private TopicPanelPersistenceService topicPanelPersistenceService;

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

    @GetMapping(value = "/second-parent")
    List<TopicPackDto> secondParent(@RequestParam("topicPackIndex") int topicPackIndex,
                                    @RequestParam("secondParentId") Long secondParentId) {
        return topicPanelService.secondParent(topicPackIndex, secondParentId);
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

    @PostMapping
    TopicPackDto addTopic(@Valid @RequestBody TopicBlockDto topicBlockDto) {
        if (topicBlockDto.getParentId() == null) {
            TopicBlock initialTopicBlock = topicPanelPersistenceService.addTopic(topicBlockDto);
            topicPanelService.initializeTopicPanel(initialTopicBlock.getTopic().getId());
            return topicPanelService.getTopicPack(0);
        } else {
            topicPanelPersistenceService.addTopicChild(topicBlockDto);
            return topicPanelService.refreshTopicPack(topicBlockDto.getParentId());
        }
    }
}
