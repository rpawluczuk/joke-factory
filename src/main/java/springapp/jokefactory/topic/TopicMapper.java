package springapp.jokefactory.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.*;

import java.util.List;
import java.util.stream.Collectors;


@Service
class TopicMapper {

    TopicItemDto mapTopicToTopicItemDto(Topic topic) {
        return new TopicItemDto(topic.getName(), topic.getId());
    }

    TopicDto mapTopicToDto (Topic topic){
        List<TopicDto> categoriesDto = null;
        if (topic.getCategories() != null) {
            categoriesDto = topic.getCategories().stream()
                    .map(TopicCategory::getCategory)
                    .map(this::mapTopicToDto)
                    .collect(Collectors.toList());
        }
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .categories(categoriesDto)
                .isCategory(topic.isCategory())
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
