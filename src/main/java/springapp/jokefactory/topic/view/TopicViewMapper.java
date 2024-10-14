package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.QuestionFacade;
import springapp.jokefactory.question.dto.QuestionDto;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicRelation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
class TopicViewMapper {

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
        List<String> connectedTopicNameList = topic.getChildren()
                .stream()
                .map(TopicRelation::getTopicChild)
                .map(Topic::getName)
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
