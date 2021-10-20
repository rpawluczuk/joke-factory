package springapp.jokefactory.topicgroup;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.categorization.Categorization;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.topic.Topic;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class TopicGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Categorization categorization;

    @ManyToOne
    private Topic connectingTopic;

    @ManyToOne
    private Topic ostensibleTopic;

    @ManyToOne
    private Topic comedyTopic;

    @ManyToOne
    private Joke joke;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;
}
