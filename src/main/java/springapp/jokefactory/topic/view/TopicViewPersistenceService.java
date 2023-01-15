package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;

@Service
public class TopicViewPersistenceService {

    @Autowired
    TopicFacade topicFacade;

    void deleteTopic(Long id) {
        topicFacade.deleteTopic(id);
    }
}
