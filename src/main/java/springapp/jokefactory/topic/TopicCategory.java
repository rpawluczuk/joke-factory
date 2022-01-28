package springapp.jokefactory.topic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Data
@NoArgsConstructor
public class TopicCategory {

    @EmbeddedId
    private TopicCategoryKey id;

    @ManyToOne
    @MapsId("topicId")
    private Topic topic;

    @ManyToOne
    @MapsId("categoryId")
    private Topic category;

    TopicCategory(Topic topic, Topic category) {
        this.id = new TopicCategoryKey(topic.getId(), category.getId());
        this.topic = topic;
        this.category = category;
    }
}
