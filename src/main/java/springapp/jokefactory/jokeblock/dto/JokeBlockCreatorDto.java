package springapp.jokefactory.jokeblock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.algorithm.diagram.DiagramBlockPresenterDto;

@Data
@Builder
@AllArgsConstructor
public class JokeBlockCreatorDto {

    private long id;
    private String jokeSnippet;
    private DiagramBlockPresenterDto structureBlockPresenterDto;
    private int position;
    private long structureId;
}
