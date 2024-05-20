package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionItemDto;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/api/topics/panel")
@CrossOrigin("http://localhost:3000")
class TopicPanelController {

    private final TopicPanelService topicPanelService;
    private final TopicPanelPersistenceService topicPanelPersistenceService;
    private final TopicPanelMapper topicPanelMapper;

    @Autowired
    public TopicPanelController(TopicPanelService topicPanelService,
                                TopicPanelPersistenceService topicPanelPersistenceService,
                                TopicPanelMapper topicPanelMapper) {
        this.topicPanelService = topicPanelService;
        this.topicPanelPersistenceService = topicPanelPersistenceService;
        this.topicPanelMapper = topicPanelMapper;
    }

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
        TopicPack topicPack = topicPanelService.changeCategoryFilter(categoryId, topicPackIndex);
        return topicPanelMapper.mapTopicPackToDto(topicPack);
    }

    @GetMapping(value = "/pack-filter/by-question")
    TopicPackDto getFilterPackByQuestion(@RequestParam("questionId") Long questionId,
                                         @RequestParam("topicPackIndex") int topicPackIndex) {
        TopicPack topicPack = topicPanelService.getFilterPackByQuestion(questionId, topicPackIndex);
        return topicPanelMapper.mapTopicPackToDto(topicPack);
    }

    @GetMapping(value = "/question-list")
    Iterable<QuestionItemDto> getQuestionItemList(@RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getQuestionItemList(topicPackIndex);
    }

    @GetMapping(value = "/change-size")
    TopicPackDto changePageSize(@RequestParam("pageSize") int pageSize,
                                @RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.changeSize(pageSize, topicPackIndex);
    }

    @PostMapping
    List<TopicPackDto> addTopic(@Valid @RequestBody TopicBlockDto topicBlockDto) {
        if (topicBlockDto.getParentId() == null) {
            TopicBlock initialTopicBlock = topicPanelPersistenceService.addTopic(topicBlockDto);
            topicPanelService.initializeTopicPanel(initialTopicBlock.getTopic().getId());
            List<TopicPackDto> list = new LinkedList<>();
            list.add(topicPanelService.getTopicPack(0));
            return list;
        } else {
            topicPanelPersistenceService.addTopicChild(topicBlockDto);
            return topicPanelService.refreshTopicPack(topicBlockDto.getParentId());
        }
    }

    @DeleteMapping(value = "/remove-relation")
    List<TopicPackDto> deleteTopicRelation(@RequestParam("topic-parent-id") Long topicParentId,
                                           @RequestParam("topic-child-id") Long topicChildId) {
        topicPanelPersistenceService.deleteTopicRelation(topicParentId, topicChildId);
        return topicPanelService.refreshTopicPack(topicParentId);
    }
}
