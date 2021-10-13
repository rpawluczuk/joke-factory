package springapp.jokefactory.topic;

import org.mapstruct.*;
import springapp.jokefactory.topic.dto.TopicCreatorChildDto;
import springapp.jokefactory.topic.dto.TopicCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.topic.dto.TopicPresenterDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
abstract class TopicMapper {

    @Mapping(target = "parentId", source = "parentId")
    abstract TopicCreatorChildDto mapTopicToTopicCreatorChildDto(Topic topic, Long parentId);

    @Mapping(target = "children", source = "topic", qualifiedByName = "extractCreatorChildDtoList")
    abstract TopicCreatorDto mapTopicToTopicCreatorDto(Topic topic, @Context Set<Topic> connectedTopicSet);

    @Named("extractCreatorChildDtoList")
    List<TopicCreatorChildDto> extractCreatorChildDtoList(Topic topic, @Context Set<Topic> connectedTopicSet) {
        return connectedTopicSet.stream()
                .map(connectedTopic -> mapTopicToTopicCreatorChildDto(connectedTopic, topic.getId()))
                .collect(Collectors.toList());
    }

    abstract Topic mapTopicCreatorChildDtoToTopic(TopicCreatorChildDto topicCreatorChildDto);

    @Mapping(target = "children", source = "connectedTopicSet", qualifiedByName = "extractTopicNameSet")
    abstract TopicPresenterDto mapTopicToTopicPresenterDto(Topic topic, Set<Topic> connectedTopicSet);

    @Named("extractTopicNameSet")
    List<String> extractTopicNameSet(Set<Topic> connectedTopicSet) {
        return connectedTopicSet.stream()
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

    @Mapping(target = "text", source = "name")
    abstract TopicItemDto mapTopicToTopicItemDto(Topic topic);

    @Mapping(target = "children", ignore = true)
    abstract Topic mapTopicCreatorDtoToTopic(TopicCreatorDto topicCreatorDto);

}
