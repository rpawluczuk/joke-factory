package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.topic.TopicDto;

import java.util.Optional;

@Component
@Data
@Builder
@Scope("prototype")
public class TopicPack {

    private TopicBlock topicBlockParent;
    private TopicBlock topicBlockSecondParent;
    private Page<TopicBlock> topicBlockPage;
    private TopicDto categoryFilter;
    private Question questionFilter;
    private boolean isAnySelection;
    private int topicPackIndex;

    Long getParentId() {
        return getTopicBlockParent().getTopic().getId();
    }

    PageRequest getPageRequest() {
        return PageRequest.of(
                getTopicBlockPage().getNumber(),
                getTopicBlockPage().getSize(),
                getTopicBlockPage().getSort()
        );
    }

    Optional<Long> getSelectedAsFirstParentId() {
        return topicBlockPage.getContent().stream()
                .filter(TopicBlock::isSelected)
                .map(TopicBlock::getTopic)
                .map(TopicDto::getId)
                .findAny();
    }

    Optional<TopicBlock> getSelectedAsSecondParent() {
        return topicBlockPage.getContent().stream()
                .filter(TopicBlock::isSecondParent)
                .findAny();
    }
}
