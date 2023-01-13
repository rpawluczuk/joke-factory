package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import springapp.jokefactory.question.QuestionFacade;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicDto;
import springapp.jokefactory.topic.TopicFacade;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
class TopicViewMapper {

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private QuestionFacade questionFacade;

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
        List<TopicDto> connectedTopicList = topicFacade.getConnectedTopicsList(topic.getId());
        List<String> connectedTopicNameList = connectedTopicList
                .stream()
                .map(TopicDto::getName)
                .collect(Collectors.toList());

        List<QuestionDto> questions = topic.getQuestionsBySource().stream()
                .map(q -> questionFacade.mapQuestionToDto(q))
                .collect(Collectors.toList());

        return TopicPresenterDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .children(connectedTopicNameList)
                .isCategory(topic.isCategory())
                .questions(questions)
                .dateCreated(new SimpleDateFormat("yyyy-MM-dd").format(topic.getDateCreated()))
                .build();
    }
}
