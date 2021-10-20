package springapp.jokefactory.topicgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.topicgroup.dto.TopicGroupCreatorDto;
import springapp.jokefactory.topicgroup.dto.TopicGroupPresenterDto;

@RestController
@RequestMapping("/api/topic-group")
@CrossOrigin("http://localhost:4200")
class TopicGroupController {

    @Autowired
    TopicGroupService topicGroupService;

    @GetMapping(value = "presenter-list/{joke_id}")
    Iterable<TopicGroupPresenterDto> getTopicGroupPresenterList(@PathVariable("joke_id") Long jokeId) {
        return topicGroupService.getTopicGroupPresenterList(jokeId);
    }

    @GetMapping(value = "creator-list/{joke_id}")
    Iterable<TopicGroupCreatorDto> getTopicGroupCreatorList(@PathVariable("joke_id") Long jokeId) {
        return topicGroupService.getTopicGroupCreatorList(jokeId);
    }
}
