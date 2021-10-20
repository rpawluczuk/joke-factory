package springapp.jokefactory.topicgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topicgroup.dto.TopicGroupPresenterDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
class TopicGroupService {

    @Autowired
    private TopicGroupRepository topicGroupRepository;

    @Autowired
    private TopicGroupMapper topicGroupMapper;

    Iterable<TopicGroupPresenterDto> getTopicGroupPresenterList(Long jokeId) {
        List<TopicGroup> topicGroupList = topicGroupRepository.findTopicGroupsByJoke_Id(jokeId);
        return topicGroupList.stream()
                .map(topicGroupMapper::topicGroupToTopicGroupPresenterDto)
                .collect(Collectors.toList());
    }
}
