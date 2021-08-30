package springapp.jokefactory.dto;

import lombok.Data;
import springapp.jokefactory.entity.Author;
import springapp.jokefactory.entity.Structure;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
public class JokeCreatorDTO {

    private Long id;
    private Set<Structure> structures;
    private Author author;
    private String origin;
    private String comedyOrigin;
    private String ostensibleOrigin;
    private List<JokeBlockDto> jokeBlocks;
    private String title;
    private String content;
    private Timestamp dateCreated;
}
