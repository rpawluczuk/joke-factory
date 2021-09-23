package springapp.jokefactory.origin;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
class OriginRelation {

    @EmbeddedId
    private OriginRelationKey id;

    @ManyToOne
    @MapsId("originParentId")
    private Origin originParent;

    @ManyToOne
    @MapsId("originChildId")
    private Origin originChild;

    OriginRelation(Origin originParent, Origin originChild) {
        this.id = new OriginRelationKey(originParent.getId(), originChild.getId());
        this.originParent = originParent;
        this.originChild = originChild;
    }
}
