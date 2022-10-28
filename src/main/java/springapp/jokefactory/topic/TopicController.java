package springapp.jokefactory.topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.joke.dto.JokePresenterDto;
import springapp.jokefactory.topic.dto.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin("http://localhost:3000")
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
    Iterable<TopicCreatorDto> getTopicCreatorChildList(@RequestParam("parent-id") Long parentId) {
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
    RandomTopicResponseDto getRandomTopicResponse(@RequestParam("randomTopicIdRequestDto") String randomTopicIdRequest) throws JsonProcessingException {
        RandomTopicIdRequestDto request = objectMapper.readValue(randomTopicIdRequest, RandomTopicIdRequestDto.class);
        return topicService.getRandomTopicResponse(request.getParentId(), request.getTotalPages(), request.getPageSize());
    }

    @GetMapping(value = "/pack-filter")
    Iterable<TopicCreatorDto> getFilteredTopicPack(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("parentId") Long parentId) {
        if (categoryId == 0) {
            return topicService.getTopicCreatorChildList(parentId);
        } else {
            return topicService.getFilteredTopicPack(categoryId, pageSize, parentId);
        }
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
    void updateTopicPagination(@RequestBody TopicPaginationDto topicPaginationDto){
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
