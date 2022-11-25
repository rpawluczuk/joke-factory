package springapp.jokefactory.topic;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.topic.dto.*;
import springapp.jokefactory.topic.panel.TopicBlockDto;
import springapp.jokefactory.topic.view.TopicPresenterDto;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
abstract class TopicMapper {

    @Autowired
    protected TopicRepository topicRepository;



//    @Mapping(target = "categories", ignore = true)
//    @Mapping(target = "parentId", source = "parentId")
//    @Mapping(target = "questions", source = "topic", qualifiedByName = "extractQuestionList")
//    abstract TopicBlockDto mapTopicToTopicDto(Topic topic, Long parentId);

    @Named("extractCategoryNameList")
    List<String> extractCategoryNameList(Topic topic) {
        return topic.getCategories().stream()
                .map(TopicCategory::getCategory)
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

    @Named("extractTopicNameList")
    List<String> extractTopicNameList(List<Topic> connectedTopicList) {
        return connectedTopicList.stream()
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

    @Named("extractQuestionList")
    List<String> extractQuestionList(Topic topic) {
        return topic.getQuestionsBySource().stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
    }

    @Mapping(target = "label", source = "name")
    @Mapping(target = "value", source = "id")
    abstract TopicItemDto mapTopicToTopicItemDto(Topic topic);
}
