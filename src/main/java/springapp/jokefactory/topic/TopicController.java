package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.topic.dto.TopicCreatorChildDto;
import springapp.jokefactory.topic.dto.TopicCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.topic.dto.TopicPresenterDto;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin("http://localhost:4200")
class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    Iterable<TopicPresenterDto> getTopicPresenterList() {
        return topicService.getTopicPresenterList();
    }

    @GetMapping(value = "/topic-creator-children", params = "parent-id")
    Iterable<TopicCreatorChildDto> getTopicCreatorChildList(@RequestParam("parent-id") Long parentId) {
        return topicService.getTopicCreatorChildList(parentId);
    }

    @GetMapping(value = "/list-items")
    Iterable<TopicItemDto> getTopicItemList() {
        return topicService.getTopicItemList();
    }

    @GetMapping(value = "/{id}")
    TopicCreatorDto getTopicCreator(@PathVariable("id") Long id) {
        return topicService.getTopicCreator(id);
    }

    @PostMapping
    void addTopic(@RequestBody TopicCreatorDto topicCreatorDTO) {
        topicService.addTopic(topicCreatorDTO);
    }

    @PostMapping(value = "/add-topic-child")
    void addTopicChild(@RequestBody TopicCreatorChildDto topicCreatorChildDto) {
        topicService.addTopicChild(topicCreatorChildDto);
    }

    @PatchMapping
    void editTopicName(@RequestBody TopicCreatorDto topicCreatorDTO) {
        topicService.editTopicName(topicCreatorDTO);
    }

    @DeleteMapping(value = "/{id}")
    void deleteTopic(@PathVariable("id") Long id) {
        topicService.deleteTopic(id);
    }

    @DeleteMapping(value = "/remove-relation", params = {"topic-parent-id", "topic-child-id"})
    void deleteTopicRelation(@RequestParam("topicParentId") Long topicParentId,
                              @RequestParam("topicChildId") Long topicChildId) {
        topicService.deleteTopicRelation(topicParentId, topicChildId);
    }
}
