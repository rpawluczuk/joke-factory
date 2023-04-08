package springapp.jokefactory.jokeblock.dto;

import lombok.Data;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockPresenterDto;

@Data
public class JokeBlockPresenterDto {

    private long id;
    private String jokeSnippet;
    private DiagramBlockPresenterDto structureBlockPresenterDto;
    private long structureId;
}
