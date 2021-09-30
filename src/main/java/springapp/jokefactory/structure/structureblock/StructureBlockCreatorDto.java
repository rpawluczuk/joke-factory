package springapp.jokefactory.structure.structureblock;

import lombok.Data;

@Data
public class StructureBlockCreatorDto {

    private Long id;
    private String title;
    private String description;
    private int position;
}
