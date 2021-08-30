package springapp.jokefactory.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.entity.StructureBlock;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class JokeBlocksAndStructureDto {

    String structureName;
    List<JokeBlockDto> jokeBlocksDto;

    public static JokeBlocksAndStructureDto create(Structure structure, Set<StructureBlock> structureBlocks) {
        List<JokeBlockDto> jokeBlockDtoList = structureBlocks.stream()
                .map(structureBlock -> JokeBlockDto.create(structureBlock, structure.getName()))
                .sorted(Comparator.comparingInt(JokeBlockDto::getPosition))
                .collect(Collectors.toList());

        return JokeBlocksAndStructureDto.builder()
                .structureName(structure.getName())
                .jokeBlocksDto(jokeBlockDtoList)
                .build();
    }
}
