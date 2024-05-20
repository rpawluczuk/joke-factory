package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.QuestionFacade;
import springapp.jokefactory.question.dto.QuestionDto;
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

    TopicViewDto mapViewToDto(Page<Topic> page) {
        Page<TopicPresenterDto> presenterPage = page.map(this::mapToDto);;

        return TopicViewDto.builder()
                .content(presenterPage.getContent())
                .number(presenterPage.getNumber())
                .size(presenterPage.getSize())
                .totalPages(presenterPage.getTotalPages())
                .totalElements(presenterPage.getTotalElements())
//                .categoryFilter(topicView.isCategoryFilter())
                .build();
    }

    TopicPresenterDto mapToDto(Topic topic) {
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

    private TopicPresenterDto mapToPresenterDto(TopicDto topicDto) {
        List<String> childrenNames = topicDto.getChildren().stream()
                .map(TopicDto::getName)
                .collect(Collectors.toList());

        return TopicPresenterDto.builder()
                .id(topicDto.getId())
                .name(topicDto.getName())
                .children(childrenNames)
                .isCategory(topicDto.isCategory())
                .dateCreated(new SimpleDateFormat("yyyy-MM-dd").format(topicDto.getDateCreated()))
                .build();
    }
}
