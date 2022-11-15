package springapp.jokefactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springapp.jokefactory.topic.TopicFacade;
import javax.transaction.Transactional;

@Component
@Scope("singleton")
public class Starter implements CommandLineRunner {

    @Autowired
    private TopicFacade topicFacade;

    @Override
    @Transactional
    public void run(String... args) {
        topicFacade.initializeTopicView();
    }
}
