package springapp.jokefactory.testutil.factory;

import org.springframework.data.domain.*;
import springapp.jokefactory.topic.Topic;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TopicFactory {

    public static Topic createParentTopic() {
        return createParentTopic(overrides -> {});
    }

    public static Topic createParentTopic(Consumer<Topic> overrides) {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setName("Parent Topic");
        overrides.accept(topic);
        return topic;
    }

    public static Topic createChildTopic() {
        return createChildTopic(overrides -> {});
    }

    public static Topic createChildTopic(Consumer<Topic> overrides) {
        var topicChild = new Topic();
        topicChild.setId(2L);
        topicChild.setName("Child Topic 1");
        overrides.accept(topicChild);
        return topicChild;
    }

    public static Page<Topic> createChildTopicsPage(int count, Topic... predefinedTopics) {
        Pageable pageable = PageRequest.of(0, 23, Sort.Direction.ASC, "name");
        return createChildTopicsPage(count, pageable, predefinedTopics);
    }

    public static Page<Topic> createChildTopicsPage(int count, Pageable pageable, Topic... predefinedTopics) {
        List<Topic> childTopics = IntStream.range(0, count)
            .mapToObj(i -> {
                if (i < predefinedTopics.length) {
                    return predefinedTopics[i];
                }
                int topicId = i + 2;
                String topicName = "Child Topic " + (topicId - 1);
                return createChildTopic(t -> {
                    t.setId((long) topicId);
                    t.setName(topicName);
                });
            })
            .collect(toList());
        return new PageImpl<>(childTopics, pageable, childTopics.size());
    }
}
