package springapp.jokefactory.topic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class TopicRelationKey implements Serializable {

    @Column(name = "topic_parent_id")
    Long topicParentId;

    @Column(name = "topic_child_id")
    Long topicChildId;

    public TopicRelationKey(Long topicParentId, Long topicChildId) {
        this.topicParentId = topicParentId;
        this.topicChildId = topicChildId;
    }
}
