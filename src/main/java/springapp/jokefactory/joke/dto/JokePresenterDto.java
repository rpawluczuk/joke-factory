package springapp.jokefactory.joke.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.structure.dto.StructurePresenterDto;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class JokePresenterDto {

    private Long id;
    private String title;
    private String content;
    private Byte rate;
    private List<StructurePresenterDto> structurePresenterList;
    private List<String> CategorizationList;
    private String author;
    private String connectingTopic;
    private String comedyTopic;
    private String ostensibleTopic;
    private String dateCreated;
    private Timestamp lastUpdated;
}
