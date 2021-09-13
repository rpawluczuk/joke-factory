package springapp.jokefactory.joke.jokeblock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class JokeBlocksAndStructureDto {

    String structureName;
    List<JokeBlockDto> jokeBlocksDto;
}
