package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.topic.dto.*;

@RestController
@RequestMapping("/api/topics/view")
@CrossOrigin("http://localhost:3000")
class TopicViewController {

    @Autowired
    private TopicViewService topicViewService;

    @GetMapping
    TopicViewDto getTopicView() {
        return topicViewService.getTopicView();
    }


    @GetMapping(value = "/change-page")
    TopicViewDto changePageNumber(@RequestParam("pageNumber") int pageNumber) {
        return topicViewService.changePage(pageNumber);
    }

    @GetMapping(value = "/change-size")
    TopicViewDto changePageSize(@RequestParam("pageSize") int pageSize) {
        return topicViewService.changeSize(pageSize);
    }

    @GetMapping(value = "/by-name", params = "name")
    TopicViewDto getTopicViewByName(@RequestParam("name") String name) {
        return topicViewService.getTopicViewByName(name);
    }

    @GetMapping(value = "/category-filter")
    TopicViewDto getViewByCategorySwitch() {
        return topicViewService.changeCategoryFilter();
    }

    @GetMapping(value = "/list-items")
    Iterable<TopicItemDto> getTopicItemList() {
        return topicViewService.getTopicItemList();
    }

    @GetMapping(value = "/category-list")
    Iterable<TopicItemDto> getCategoryItemList() {
        return topicViewService.getCategoryItemList();
    }

//    @GetMapping(value = "/{id}")
//    TopicBlockDto getTopic(@PathVariable("id") Long id) {
//        if (id == 0L) {
//            return new TopicBlockDto();
//        }
//        return topicService.getTopic(id);
//    }
//
//    @GetMapping(value = "/pagination")
//    TopicPaginationDto getTopicPagination() {
//        return topicService.getTopicPagination();
//    }
//
//
//
//

//
//    @PutMapping(value = "/pagination")
//    void updateTopicPagination(@RequestBody TopicPaginationDto topicPaginationDto) {
//        topicService.updateTopicPagination(topicPaginationDto);
//    }
//
//    @PatchMapping
//    void editTopicName(@RequestBody TopicCreatorDto topicCreatorDTO) {
//        topicService.editTopicName(topicCreatorDTO);
//    }
//
//    @PatchMapping(value = "/changeCategoryStatus/{id}")
//    void changeCategoryStatus(@PathVariable Long id) {
//        topicService.changeCategoryStatus(id);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    void deleteTopic(@PathVariable("id") Long id) {
//        topicService.deleteTopic(id);
//    }
//
//    @DeleteMapping(value = "/remove-relation")
//    void deleteTopicRelation(@RequestParam("topic-parent-id") Long topicParentId,
//                             @RequestParam("topic-child-id") Long topicChildId) {
//        topicService.deleteTopicRelation(topicParentId, topicChildId);
//    }
}
