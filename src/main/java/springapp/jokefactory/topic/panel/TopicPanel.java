package springapp.jokefactory.topic.panel;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.topic.Topic;

import java.util.LinkedList;
import java.util.List;

@Component
@Data
@Scope("singleton")
public class TopicPanel {

    private Topic initialTopic;
    private List<TopicPack> topicPackList;

    public void clearPanel() {
        this.initialTopic = Topic.getBasicTopic();
        this.topicPackList = new LinkedList<>();
    }

    public void addTopicPack(TopicPack topicPack) {
        if (topicPackList.isEmpty()) {
            setInitialTopic(topicPack.getTopicParent());
        }
        topicPackList.add(topicPack);
    }

    public void addTopicPack(TopicPack topicPack, int topicPackIndex) {
        topicPackList.add(topicPackIndex + 1, topicPack);
        topicPackList = topicPackList.subList(0, topicPackIndex + 2);
    }

    public TopicPack changeTopicPage(int topicPackIndex, Page<Topic> topicPage) {
        topicPackList.get(topicPackIndex).setTopicPage(topicPage);
        if (topicPackList.size() > topicPackIndex + 1) {
            Long selectedTopicId = topicPackList.get(topicPackIndex + 1).getTopicParent().getId();
            selectTopic(topicPackIndex, selectedTopicId);
        }
        return topicPackList.get(topicPackIndex);
    }

    public void changeSelectedTopic(int topicPackIndex, Long newlySelectedTopicId) {
        deselectTopic(topicPackIndex);
        selectTopic(topicPackIndex, newlySelectedTopicId);
    }

    public Topic getTopicParent(int topicPackIndex) {
        return topicPackList.get(topicPackIndex).getTopicParent();
    }

    public Page<Topic> getTopicPage(int topicPackIndex) {
        return topicPackList.get(topicPackIndex).getTopicPage();
    }

    public void setCategoryFilter(Topic categoryFilter, int topicPackIndex) {
        topicPackList.get(topicPackIndex).setCategoryFilter(categoryFilter);
    }

    public void setQuestionFilter(Question questionFilter, int topicPackIndex) {
        topicPackList.get(topicPackIndex).setQuestionFilter(questionFilter);
    }

    private void selectTopic(int topicPackIndex, Long topicIdToSelect) {
        topicPackList.get(topicPackIndex).getTopicPage().getContent().stream()
                .filter(topic -> topic.getId().equals(topicIdToSelect))
                .findAny()
                .ifPresent(topic -> topic.setSelected(true));
    }

    private void deselectTopic(int topicPackIndex) {
        topicPackList.get(topicPackIndex).getTopicPage().getContent().stream()
                .filter(Topic::isSelected)
                .findAny()
                .ifPresent(topic -> topic.setSelected(false));
    }
}
