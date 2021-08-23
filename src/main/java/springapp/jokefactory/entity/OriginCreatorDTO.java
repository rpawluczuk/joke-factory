package springapp.jokefactory.entity;

import lombok.Data;

import java.util.Set;

@Data
public class OriginCreatorDTO {

    private long id;
    private String name;
    private Set<OriginCreatorDTO> children;
    private Set<OriginCreatorDTO> parents;
}
