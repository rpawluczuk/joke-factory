package springapp.jokefactory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class OriginRelationKey implements Serializable {

    @Column(name = "origin_parent_id")
    Long originParentId;

    @Column(name = "origin_child_id")
    Long originChildId;

}
