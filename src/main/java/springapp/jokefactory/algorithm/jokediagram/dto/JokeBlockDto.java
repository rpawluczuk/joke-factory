package springapp.jokefactory.algorithm.jokediagram.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockDto;

@Data
@Builder
public class JokeBlockDto {

    private long id;
    private String jokeSnippet;
    private String title;
    private String description;
    private int position;
    private long algorithmId;
}
