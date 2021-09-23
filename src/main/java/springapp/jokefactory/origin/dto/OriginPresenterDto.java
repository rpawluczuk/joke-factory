package springapp.jokefactory.origin.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OriginPresenterDto {

    private Long id;
    private String name;
    private List<String> children;
    private Timestamp dateCreated;
}
