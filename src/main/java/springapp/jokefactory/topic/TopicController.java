package springapp.jokefactory.topic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.topic.dto.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin("http://localhost:3000")
class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicPanelService topicPanelService;

    @GetMapping
    Iterable<TopicPresenterDto> getTopicPresenterList() {
        return topicService.getTopicPresenterList();
    }

    @GetMapping(value = "/by-name", params = "name")
    Iterable<TopicPresenterDto> getTopicPresenterListByName(@RequestParam("name") String name) {
        return topicService.getTopicPresenterListByName(name);
    }

    @GetMapping(value = "/list-items")
    Iterable<TopicItemDto> getTopicItemList() {
        return topicService.getTopicItemList();
    }

    @GetMapping(value = "/category-list")
    Iterable<TopicItemDto> getCategoryList() {
        return topicService.getCategoryList();
    }

    @GetMapping(value = "/{id}")
    TopicDto getTopic(@PathVariable("id") Long id) {
        if (id == 0L) {
            return new TopicDto();
        }
        return topicService.getTopic(id);
    }

    @GetMapping(value = "/panel/{initialId}")
    TopicPanelDto getTopicPanel(@PathVariable("initialId") Long initialId) {
        return topicService.getTopicPanel(initialId);
    }

    @GetMapping(value = "/panel/get-pack-by-page")
    TopicPackDto getPackByPage(@RequestParam("pageNumber") int pageNumber,
                               @RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getPackByPage(topicPackIndex, pageNumber);
    }

    @GetMapping(value = "/panel/show-children")
    List<TopicPackDto> showChildren(@RequestParam("topicPackIndex") int topicPackIndex,
                                    @RequestParam("parentId") Long parentId) {
        return topicPanelService.showChildren(topicPackIndex, parentId);
    }

    @GetMapping(value = "/pagination")
    TopicPaginationDto getTopicPagination() {
        return topicService.getTopicPagination();
    }

    @GetMapping(value = "/panel/random")
    List<TopicPackDto> getRandomTopicResponse(@RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getRandomTopicPack(topicPackIndex);
    }

    @GetMapping(value = "/panel/pack-filter")
    TopicPackDto getFilteredTopicPack(@RequestParam("categoryId") Long categoryId,
                                      @RequestParam("topicPackIndex") int topicPackIndex) {
        return topicPanelService.getFilteredTopicPack(categoryId, topicPackIndex);
    }

    @PostMapping
    TopicCreatorDto addTopic(@Valid @RequestBody TopicCreatorDto topicCreatorDto) {
        if (topicCreatorDto.getParentId() == null) {
            return topicService.addTopic(topicCreatorDto);
        } else {
            return topicService.addTopicChild(topicCreatorDto);
        }
    }

    @PutMapping(value = "/pagination")
    void updateTopicPagination(@RequestBody TopicPaginationDto topicPaginationDto) {
        topicService.updateTopicPagination(topicPaginationDto);
    }

    @PatchMapping
    void editTopicName(@RequestBody TopicCreatorDto topicCreatorDTO) {
        topicService.editTopicName(topicCreatorDTO);
    }

    @PatchMapping(value = "/changeCategoryStatus/{id}")
    void changeCategoryStatus(@PathVariable Long id) {
        topicService.changeCategoryStatus(id);
    }

    @DeleteMapping(value = "/{id}")
    void deleteTopic(@PathVariable("id") Long id) {
        topicService.deleteTopic(id);
    }

    @DeleteMapping(value = "/remove-relation")
    void deleteTopicRelation(@RequestParam("topic-parent-id") Long topicParentId,
                             @RequestParam("topic-child-id") Long topicChildId) {
        topicService.deleteTopicRelation(topicParentId, topicChildId);
    }
}
