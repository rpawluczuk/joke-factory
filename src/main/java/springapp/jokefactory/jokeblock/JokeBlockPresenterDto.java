package springapp.jokefactory.jokeblock;

import lombok.Data;
import springapp.jokefactory.structureblock.dto.StructureBlockPresenterDto;

@Data
public class JokeBlockPresenterDto {

    private long id;
    private String jokeSnippet;
    private StructureBlockPresenterDto structureBlockPresenterDto;
    private long structureId;
}
