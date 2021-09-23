package springapp.jokefactory.joke;

import lombok.Data;
import springapp.jokefactory.structure.StructurePresenterDto;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class JokePresenterDto {

    private Long id;
    private String title;
    private String content;
    private Set<StructurePresenterDto> structures;
    private String author;
    private String connectingOrigin;
    private String comedyOrigin;
    private String ostensibleOrigin;
    private Timestamp dateCreated;
    private Timestamp lastUpdated;
}
