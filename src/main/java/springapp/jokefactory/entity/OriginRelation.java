package springapp.jokefactory.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OriginRelation {

    @EmbeddedId
    private OriginRelationKey id;

    @ManyToOne
    @MapsId("originParentId")
    private Origin originParent;

    @ManyToOne
    @MapsId("originChildId")
    private Origin originChild;
}
