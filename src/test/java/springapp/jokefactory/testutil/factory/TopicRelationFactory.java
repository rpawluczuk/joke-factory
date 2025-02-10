package springapp.jokefactory.testutil.factory;

import springapp.jokefactory.topic.TopicRelation;

import java.util.function.Consumer;

public class TopicRelationFactory {

    public static TopicRelation create() {
        return create(overrides -> {});
    }

    public static TopicRelation create(Consumer<TopicRelation> overrides) {
        var topicRelation = new TopicRelation();
        topicRelation.setTopicParent(TopicFactory.createParentTopic());
        topicRelation.setTopicChild(TopicFactory.createChildTopic());
        overrides.accept(topicRelation);
        return topicRelation;
    }
}
