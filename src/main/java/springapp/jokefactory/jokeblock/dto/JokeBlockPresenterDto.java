package springapp.jokefactory.jokeblock.dto;

import lombok.Data;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockDto;

@Data
public class JokeBlockPresenterDto {

    private long id;
    private String jokeSnippet;
    private DiagramBlockDto structureBlockPresenterDto;
    private long structureId;
}
