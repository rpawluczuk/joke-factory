package springapp.jokefactory.topic.panel;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicDto;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
@Data
@Scope("singleton")
public class TopicPanel {

    private TopicBlock initialTopicBlock;
    private List<TopicPack> topicPackList;

    public void clearPanel() {
        this.initialTopicBlock = TopicBlock.builder()
                .topic(TopicDto.getBasicTopic())
                .build();
        this.topicPackList = new LinkedList<>();
    }

    public void addTopicPack(TopicPack topicPack) {
        if (topicPackList.isEmpty()) {
            setInitialTopicBlock(topicPack.getTopicBlockParent());
        }
        topicPackList.add(topicPack);
    }

    public void addTopicPack(TopicPack topicPack, int topicPackIndex) {
        topicPack.setTopicPackIndex(topicPackIndex + 1);
        topicPackList.add(topicPackIndex + 1, topicPack);
        topicPackList = topicPackList.subList(0, topicPackIndex + 2);
    }

    public TopicPack changeTopicPage(int topicPackIndex, Page<TopicBlock> topicPage) {
        topicPackList.get(topicPackIndex).setTopicBlockPage(topicPage);
        if (topicPackList.size() > topicPackIndex + 1) {
            Long selectedTopicId = topicPackList.get(topicPackIndex + 1).getTopicBlockParent().getTopic().getId();
            selectTopic(topicPackIndex, selectedTopicId);
        }
        return topicPackList.get(topicPackIndex);
    }

    public TopicBlock getTopicBlockParent(int topicPackIndex) {
        return topicPackList.get(topicPackIndex).getTopicBlockParent();
    }

    public Page<TopicBlock> getTopicBlockPage(int topicPackIndex) {
        return topicPackList.get(topicPackIndex).getTopicBlockPage();
    }

    public void setCategoryFilter(TopicDto categoryFilter, int topicPackIndex) {
        topicPackList.get(topicPackIndex).setCategoryFilter(categoryFilter);
    }

    public void setQuestionFilter(Question questionFilter, int topicPackIndex) {
        topicPackList.get(topicPackIndex).setQuestionFilter(questionFilter);
    }

    void selectTopic(int topicPackIndex, Long topicIdToSelect) {
        topicPackList.get(topicPackIndex).getTopicBlockPage().getContent().stream()
                .filter(topicBlock -> topicBlock.getTopic().getId().equals(topicIdToSelect))
                .findAny()
                .ifPresent(topic -> {
                    topic.setSelected(true);
                    topicPackList.get(topicPackIndex).setAnySelection(true);
                });
    }

    void deselectTopic(int topicPackIndex) {
        topicPackList.get(topicPackIndex).getTopicBlockPage().getContent().stream()
                .filter(TopicBlock::isSelected)
                .findAny()
                .ifPresent(topic -> {
                    topic.setSelected(false);
                    topicPackList.get(topicPackIndex).setAnySelection(false);
                });
    }

    Optional<TopicBlock> findSelectedTopicBlock(int topicPackIndex) {
        return topicPackList.get(topicPackIndex).getTopicBlockPage().getContent().stream()
                .filter(TopicBlock::isSelected)
                .findAny();
    }

    void deselectPreviousSecondParentTopic(int topicPackIndex) {
        topicPackList.get(topicPackIndex).getTopicBlockPage().getContent().stream()
                .filter(TopicBlock::isSecondParent)
                .findAny()
                .ifPresent(topic -> topic.setSecondParent(false));
    }

    void selectSecondParentTopic(int topicPackIndex, Long secondParentTopicId) {
        topicPackList.get(topicPackIndex).getTopicBlockPage().getContent().stream()
                .filter(topicBlock -> topicBlock.getTopic().getId().equals(secondParentTopicId))
                .findAny()
                .ifPresent(topic -> topic.setSecondParent(true));
    }

    Optional<TopicBlock> findSecondParentTopicBlock(int topicPackIndex) {
        return topicPackList.get(topicPackIndex).getTopicBlockPage().getContent().stream()
                .filter(TopicBlock::isSecondParent)
                .findAny();
    }
}
