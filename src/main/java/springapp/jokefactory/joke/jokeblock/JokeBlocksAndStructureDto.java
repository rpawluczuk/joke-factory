package springapp.jokefactory.joke.jokeblock;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JokeBlocksAndStructureDto {

    String structureName;
    List<JokeBlockDto> jokeBlocksDto;
}
