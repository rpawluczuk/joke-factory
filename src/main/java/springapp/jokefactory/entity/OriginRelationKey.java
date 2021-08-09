package springapp.jokefactory.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class OriginRelationKey implements Serializable {

    @Column(name = "origin_parent_id")
    Long originParentId;

    @Column(name = "origin_child_id")
    Long originChildId;

    public OriginRelationKey(Long originParentId, Long originChildId) {
        this.originParentId = originParentId;
        this.originChildId = originChildId;
    }
}
