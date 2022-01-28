package springapp.jokefactory.topic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class TopicCategoryKey implements Serializable  {

    Long topicId;

    Long categoryId;

    public TopicCategoryKey(Long topicId, Long categoryId) {
        this.topicId = topicId;
        this.categoryId = categoryId;
    }
}
