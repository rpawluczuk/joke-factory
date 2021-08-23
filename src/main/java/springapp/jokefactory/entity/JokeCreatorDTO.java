package springapp.jokefactory.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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
    private List<JokeBlock> jokeBlocks;
    private String title;
    private String content;
    private Timestamp dateCreated;
}
