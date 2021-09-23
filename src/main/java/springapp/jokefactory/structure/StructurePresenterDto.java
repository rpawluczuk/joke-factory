package springapp.jokefactory.structure;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StructurePresenterDto {

    private Long id;
    private String name;
    private String description;
    private Timestamp dateCreated;
}
