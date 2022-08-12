package springapp.jokefactory.topic;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.topic.dto.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
abstract class TopicMapper {

    @Autowired
    protected TopicRepository topicRepository;

    @Mapping(target = "categories", source = "topic", qualifiedByName = "extractCategoryNameList")
    @Mapping(target = "parentId", source = "parentId")
    abstract TopicCreatorChildDto mapTopicToTopicCreatorChildDto(Topic topic, Long parentId);

    @Named("extractCategoryNameList")
    List<String> extractCategoryNameList(Topic topic) {
        return topic.getCategories().stream()
                .map(TopicCategory::getCategory)
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

//    abstract TopicCreatorChildDto mapTopicToTopicCreatorChildDto(Topic topic);

    @Mapping(target = "children", source = "topic", qualifiedByName = "extractCreatorChildDtoList")
    abstract TopicCreatorDto mapTopicToTopicCreatorDto(Topic topic);

    @Named("extractCreatorChildDtoList")
    List<TopicCreatorChildDto> extractCreatorChildDtoList(Topic topic) {
        List<Topic> connectedTopicList = topicRepository.findAllConnectedTopics(topic);
        return connectedTopicList.stream()
                .map(connectedTopic -> mapTopicToTopicCreatorChildDto(connectedTopic, topic.getId()))
                .collect(Collectors.toList());
    }

    @Mapping(target = "categories", ignore = true)
    abstract Topic mapTopicCreatorChildDtoToTopic(TopicCreatorChildDto topicCreatorChildDto);

    @Mapping(target = "children", source = "connectedTopicList", qualifiedByName = "extractTopicNameList")
    @Mapping(target = "questions", source = "topic", qualifiedByName = "extractQuestionList")
    abstract TopicPresenterDto mapTopicToTopicPresenterDto(Topic topic, List<Topic> connectedTopicList);

    @Named("extractTopicNameList")
    List<String> extractTopicNameList(List<Topic> connectedTopicList) {
        return connectedTopicList.stream()
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

    @Named("extractQuestionList")
    List<String> extractQuestionList(Topic topic) {
        return topic.getQuestions().stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
    }

    @Mapping(target = "label", source = "name")
    @Mapping(target = "value", source = "id")
    abstract TopicItemDto mapTopicToTopicItemDto(Topic topic);

    @Mapping(target = "children", ignore = true)
    abstract Topic mapTopicCreatorDtoToTopic(TopicCreatorDto topicCreatorDto);
}
