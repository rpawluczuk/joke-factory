package springapp.jokefactory.topicgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.topicgroup.dto.TopicGroupCreatorDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicGroupFacade {

    @Autowired
    private TopicGroupMapper topicGroupMapper;

    @Autowired
    private TopicGroupRepository topicGroupRepository;

    public List<TopicGroup> extractTopicGroupList(List<TopicGroupCreatorDto> topicGroupCreatorList, Joke joke) {
        return topicGroupCreatorList.stream()
                .map(topicGroupCreatorDto -> topicGroupMapper.topicGroupCreatorDtoToTopicGroup(topicGroupCreatorDto,joke))
                .collect(Collectors.toList());
    }

    public void saveTopicGroupList(List<TopicGroup> topicGroupList) {
        topicGroupList.forEach(topicGroupRepository::save);
    }
}
