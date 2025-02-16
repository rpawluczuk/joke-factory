package springapp.jokefactory.topic.panel;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.question.dto.QuestionItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/topics/panel")
@CrossOrigin("http://localhost:3000")
class TopicPanelController {

    private static final Logger logger = LoggerFactory.getLogger(TopicPanelController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final TopicPanelService topicPanelService;
    private final TopicPanelPersistenceService topicPanelPersistenceService;


    @Autowired
    public TopicPanelController(TopicPanelService topicPanelService,
                                TopicPanelPersistenceService topicPanelPersistenceService,
                                TopicPanelMapper topicPanelMapper) {
        this.topicPanelService = topicPanelService;
        this.topicPanelPersistenceService = topicPanelPersistenceService;
    }

    @GetMapping(value = "/{id}")
    TopicBlockDto getTopicPanel(@PathVariable("id") Long id) {
        return topicPanelService.getTopicBlock(id);
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

    @PostMapping(value = "/get-pack")
    TopicPackDto getPack(@Valid @RequestBody PackRequest request) {
        return topicPanelService.getPack(request);
    }

//    @GetMapping(value = "/second-parent")
//    List<TopicPackDto> secondParent(@RequestParam("topicPackIndex") int topicPackIndex,
//                                    @RequestParam("secondParentId") Long secondParentId) {
//        return topicPanelService.secondParent(topicPackIndex, secondParentId);
//    }

    @GetMapping(value = "/random")
    List<TopicPackDto> getRandomTopicResponse(@RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getRandomTopicPack(topicPackIndex);
    }

//    @GetMapping(value = "/pack-filter")
//    TopicPackDto getFilteredTopicPack(@RequestParam("categoryId") Long categoryId,
//                                      @RequestParam("topicPackIndex") int topicPackIndex) {
//        TopicPack topicPack = topicPanelService.changeCategoryFilter(categoryId, topicPackIndex);
//        return topicPanelMapper.mapTopicPackToDto(topicPack);
//    }

//    @GetMapping(value = "/pack-filter/by-question")
//    TopicPackDto getFilterPackByQuestion(@RequestParam("questionId") Long questionId,
//                                         @RequestParam("topicPackIndex") int topicPackIndex) {
//        TopicPack topicPack = topicPanelService.getFilterPackByQuestion(questionId, topicPackIndex);
//        return topicPanelMapper.mapTopicPackToDto(topicPack);
//    }

//    @GetMapping(value = "/question-list")
//    Iterable<QuestionItemDto> getQuestionItemList(@RequestParam("topicId") Long topicId) {
//        return topicPanelService.getQuestionItemList(topicId);
//    }

    @PostMapping
    TopicPackDto addTopic(@Valid @RequestBody TopicBlockDto topicBlockDto) throws JsonProcessingException {
        TopicPackDto  response =  topicPanelService.addTopic(topicBlockDto);
        logger.info("Response sent: {}", OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PatchMapping
    TopicPackDto editTopic(@Valid @RequestBody TopicBlockDto topicBlockDto) {
        return topicPanelService.editTopic(topicBlockDto);
    }

    @DeleteMapping(value = "/remove-relation")
    void deleteTopicRelation(@RequestParam("topic-parent-id") Long topicParentId,
                             @RequestParam("topic-child-id") Long topicChildId) {
        topicPanelPersistenceService.deleteTopicRelation(topicParentId, topicChildId);
    }
}
