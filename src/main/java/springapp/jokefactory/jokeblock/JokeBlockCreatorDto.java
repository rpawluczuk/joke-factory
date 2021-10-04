package springapp.jokefactory.jokeblock;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.structureblock.dto.StructureBlockPresenterDto;

@Data
@Builder
public class JokeBlockCreatorDto {

    private long id;
    private String jokeSnippet;
    private StructureBlockPresenterDto structureBlockPresenterDto;
    private int position;
    private long structureId;
}
