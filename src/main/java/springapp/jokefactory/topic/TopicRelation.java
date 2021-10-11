package springapp.jokefactory.topic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
class TopicRelation {

    @EmbeddedId
    private TopicRelationKey id;

    @ManyToOne
    @MapsId("topicParentId")
    private Topic topicParent;

    @ManyToOne
    @MapsId("topicChildId")
    private Topic topicChild;

    TopicRelation(Topic topicParent, Topic topicChild) {
        this.id = new TopicRelationKey(topicParent.getId(), topicChild.getId());
        this.topicParent = topicParent;
        this.topicChild = topicChild;
    }
}
