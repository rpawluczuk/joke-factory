package springapp.jokefactory.topic;

public class TopicFactory {

    public Topic createTopic(int index) {
        return Topic.builder()
                .id((long) index)
                .name("topic name " + index)
                .build();
    }
}
