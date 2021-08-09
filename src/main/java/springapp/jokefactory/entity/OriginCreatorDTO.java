package springapp.jokefactory.entity;

import lombok.Data;

import java.util.Set;

@Data
public class OriginCreatorDTO {

    private String name;
    private Set<OriginCreatorDTO> children;
}
