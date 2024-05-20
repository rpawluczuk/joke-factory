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

    TopicItemDto toItemDto(Topic topic) {
        return new TopicItemDto(topic.getName(), topic.getId());
    }

    TopicDto toDto(Topic topic) {
        List<TopicDto> categoriesDto = topic.getCategories() == null ?
                List.of() :
                topic.getCategories().stream()
                        .map(TopicCategory::getCategory)
                        .map(this::toDtoWithoutRelations)
                        .collect(Collectors.toList());

        List<TopicDto> childrenDto = topic.getChildren() == null ?
                List.of() :
                topic.getChildren().stream()
                        .map(TopicRelation::getTopicChild)
                        .map(this::toDtoWithoutRelations)
                        .collect(Collectors.toList());
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .categories(categoriesDto)
                .children(childrenDto)
                .isCategory(topic.isCategory())
                .dateCreated(topic.getDateCreated())
                .build();
    }

    private TopicDto toDtoWithoutRelations(Topic topic) {
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .isCategory(topic.isCategory())
                .dateCreated(topic.getDateCreated())
                .build();
    }

    Page<TopicDto> toDtoPage(Page<Topic> topicPage, PageRequest pageRequest) {
        List<TopicDto> content = topicPage.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageRequest, topicPage.getTotalElements());
    }

    Topic fromDto(TopicDto topicDto) {
        return Topic.builder()
                .name(topicDto.getName())
                .build();
    }

    public TopicItemDto mapTopicDtoToTopicItemDto(TopicDto topicDto) {
        return new TopicItemDto(topicDto.getName(), topicDto.getId());
    }
}
