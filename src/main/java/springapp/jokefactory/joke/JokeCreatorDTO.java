package springapp.jokefactory.joke;

import lombok.Data;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.joke.jokeblock.JokeBlocksAndStructureDto;

import java.sql.Timestamp;
import java.util.List;

@Data
public class JokeCreatorDTO {

    private Long id;
    private Author author;
    private String origin;
    private String comedyOrigin;
    private String ostensibleOrigin;
    private List<JokeBlocksAndStructureDto> jokeBlocksWithStructureDtoList;
    private String title;
    private String content;
    private Timestamp dateCreated;
}
