package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
class TopicViewMapper {

    @Autowired
    private TopicFacade topicFacade;

    TopicViewDto mapViewToDto(TopicView topicView) {
        List<TopicPresenterDto> content = topicView.getTopicPage().getContent().stream()
                .map(this::mapTopicToDto)
                .collect(Collectors.toList());

        return TopicViewDto.builder()
                .content(content)
                .number(topicView.getTopicPage().getNumber())
                .size(topicView.getTopicPage().getSize())
                .totalPages(topicView.getTopicPage().getTotalPages())
                .totalElements(topicView.getTopicPage().getTotalElements())
                .categoryFilter(topicView.isCategoryFilter())
                .build();
    }


    TopicPresenterDto mapTopicToDto(Topic topic) {
        List<Topic> connectedTopicList = topicFacade.getConnectedTopicsList(topic);
        List<String> connectedTopicNameList = connectedTopicList
                .stream()
                .map(Topic::getName)
                .collect(Collectors.toList());

        return TopicPresenterDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .children(connectedTopicNameList)
                .isCategory(topic.isCategory())
                .dateCreated(new SimpleDateFormat("yyyy-MM-dd").format(topic.getDateCreated()))
                .build();
    }
}
