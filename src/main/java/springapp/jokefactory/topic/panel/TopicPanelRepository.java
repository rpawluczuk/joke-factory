package springapp.jokefactory.topic.panel;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.topic.Topic;

interface TopicPanelRepository extends JpaRepository<Topic, Long> {

}
