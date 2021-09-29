package springapp.jokefactory.joke.jokeblock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JokeBlockDto {

    private long id;
    private String jokeSnippet;
    private String title;
    private String description;
    private int position;
    private long structureId;
    private long structureBlockId;
}
