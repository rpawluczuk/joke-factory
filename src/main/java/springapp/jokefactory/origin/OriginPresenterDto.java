package springapp.jokefactory.origin;

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
