package springapp.jokefactory.topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.topic.dto.*;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin("http://localhost:4200")
class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    Iterable<TopicPresenterDto> getTopicPresenterList() {
        return topicService.getTopicPresenterList();
    }

    @GetMapping(value = "/by-name", params = "name")
    Iterable<TopicPresenterDto> getTopicPresenterListByName(@RequestParam("name") String name) {
        return topicService.getTopicPresenterListByName(name);
    }

    @GetMapping(value = "/topic-creator-children", params = "parent-id")
    Iterable<TopicCreatorChildDto> getTopicCreatorChildList(@RequestParam("parent-id") Long parentId) {
        return topicService.getTopicCreatorChildList(parentId);
    }

    @GetMapping(value = "/topic-creator-child-row")
    TopicCreatorChildRowResponseDto getTopicCreatorChildRowAndPage(@RequestParam("topicCreatorChildRowRequestDto") String topicCreatorChildRowRequestDto) throws JsonProcessingException {
        TopicCreatorChildRowRequestDto request = objectMapper.readValue(topicCreatorChildRowRequestDto, TopicCreatorChildRowRequestDto.class);
        if (request.getParentId() == null) {
            return topicService.getAllTopicCreatorChildPage(request);
        }
        return topicService.getTopicCreatorChildRowAndPage(request);
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
    TopicCreatorDto getTopicCreator(@PathVariable("id") Long id) {
        return topicService.getTopicCreator(id);
    }

    @GetMapping(value = "/pagination")
    TopicPaginationDto getTopicPagination(){
        return topicService.getTopicPagination();
    }

    @GetMapping(value = "/random")
    RandomTopicIdResponseDto getRandomTopicResponse(@RequestParam("randomTopicIdRequestDto") String randomTopicIdRequest) throws JsonProcessingException {
        RandomTopicIdRequestDto request = objectMapper.readValue(randomTopicIdRequest, RandomTopicIdRequestDto.class);
        return topicService.getRandomTopicResponse(request);
    }

    @PostMapping
    void addTopic(@RequestBody TopicCreatorDto topicCreatorDTO) {
        topicService.addTopic(topicCreatorDTO);
    }

    @PostMapping(value = "/add-topic-child")
    void addTopicChild(@RequestBody TopicCreatorChildDto topicCreatorChildDto) {
        topicService.addTopicChild(topicCreatorChildDto);
    }

    @PutMapping(value = "/pagination")
    void updateTopicPagination(@RequestBody TopicPaginationDto topicPaginationDto){
        topicService.updateTopicPagination(topicPaginationDto);
    }

    @PatchMapping
    void editTopicName(@RequestBody TopicCreatorDto topicCreatorDTO) {
        topicService.editTopicName(topicCreatorDTO);
    }

    @PatchMapping(value = "/changeCategoryStatus")
    void changeCategoryStatus(@RequestBody Long id) {
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
