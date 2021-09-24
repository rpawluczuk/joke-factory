package springapp.jokefactory.joke.jokeblock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.structure.StructureItemDto;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class JokeBlocksAndStructureDto {

    StructureItemDto structureItemDto;
    List<JokeBlockDto> jokeBlocksDto;
}
