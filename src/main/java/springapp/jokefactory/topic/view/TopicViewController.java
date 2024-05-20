package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.topic.dto.*;

@RestController
@RequestMapping("/api/topics/view")
@CrossOrigin("http://localhost:3000")
class TopicViewController {

    private final TopicViewService topicViewService;

    private final TopicViewPersistenceService topicViewPersistenceService;

    @Autowired
    public TopicViewController(TopicViewService topicViewService,
                               TopicViewPersistenceService topicViewPersistenceService) {
        this.topicViewService = topicViewService;
        this.topicViewPersistenceService = topicViewPersistenceService;
    }

    @GetMapping
    TopicViewDto getTopicView() {
        return topicViewService.getTopicView();
    }


//    @GetMapping(value = "/change-page")
//    TopicViewDto changePageNumber(@RequestParam("pageNumber") int pageNumber) {
//        return topicViewService.changePage(pageNumber);
//    }
//
//    @GetMapping(value = "/change-size")
//    TopicViewDto changePageSize(@RequestParam("pageSize") int pageSize) {
//        return topicViewService.changeSize(pageSize);
//    }
//
//    @GetMapping(value = "/by-name", params = "name")
//    TopicViewDto getTopicViewByName(@RequestParam("name") String name) {
//        return topicViewService.getTopicViewByName(name);
//    }
//
//    @GetMapping(value = "/category-filter")
//    TopicViewDto getViewByCategorySwitch() {
//        return topicViewService.changeCategoryFilter();
//    }

    @GetMapping(value = "/list-items")
    Iterable<TopicItemDto> getTopicItemList() {
        return topicViewService.getTopicItemList();
    }

    @GetMapping(value = "/category-list")
    Iterable<TopicItemDto> getCategoryItemList() {
        return topicViewService.getCategoryItemList();
    }

//    @DeleteMapping(value = "/{id}")
//    TopicViewDto deleteTopic(@PathVariable("id") Long id) {
//        topicViewPersistenceService.deleteTopic(id);
//        return topicViewService.refreshTopicView();
//    }

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
    @PatchMapping(value = "/changeCategoryStatus/{id}")
    void changeCategoryStatus(@PathVariable Long id) {
        topicViewService.changeCategoryStatus(id);
    }
}
