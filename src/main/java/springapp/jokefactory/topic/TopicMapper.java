package springapp.jokefactory.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.*;
import springapp.jokefactory.topic.panel.TopicBlock;

import java.util.stream.Collectors;


@Service
class TopicMapper {

    TopicItemDto mapTopicToTopicItemDto(Topic topic) {
        return new TopicItemDto(topic.getName(), topic.getId());
    }

    TopicDto mapTopicToDto (Topic topic){
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }

    Page<TopicDto> mapTopicPageToDto(Page<Topic> topicPage, PageRequest pageRequest) {
        return new PageImpl<>(
                topicPage.getContent().stream()
                        .map(this::mapTopicToDto)
                        .collect(Collectors.toList()),
                pageRequest, topicPage.getTotalElements()
        );
    }

    Topic dtoToTopic(TopicDto topicDto) {
        return Topic.builder()
                .name(topicDto.getName())
                .build();
    }

    public TopicItemDto mapTopicDtoToTopicItemDto(TopicDto topicDto) {
        return new TopicItemDto(topicDto.getName(), topicDto.getId());
    }
}
