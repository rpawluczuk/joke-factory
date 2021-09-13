package springapp.jokefactory.joke.jokeblock;

import lombok.Builder;
import lombok.Data;
import org.mapstruct.factory.Mappers;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.structureblock.StructureBlock;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class JokeBlocksAndStructureDto {

    String structureName;
    List<JokeBlockDto> jokeBlocksDto;
    private static JokeBlockMapper jokeBlockMapper = Mappers.getMapper(JokeBlockMapper.class);

    public static JokeBlocksAndStructureDto create(Structure structure, Set<StructureBlock> structureBlocks) {
        List<JokeBlockDto> jokeBlockDtoList = structureBlocks.stream()
                .map(structureBlock -> jokeBlockMapper.structureBlockToJokeBlockDto(structureBlock))
                .sorted(Comparator.comparingInt(JokeBlockDto::getPosition))
                .collect(Collectors.toList());

        return JokeBlocksAndStructureDto.builder()
                .structureName(structure.getName())
                .jokeBlocksDto(jokeBlockDtoList)
                .build();
    }

    public static List<JokeBlocksAndStructureDto> create(List<JokeBlock> jokeBlocks) {
        Map<Structure, List<JokeBlock>> jokeBlockMap = jokeBlocks.stream()
                .collect(Collectors.groupingBy(jokeBlock -> jokeBlock.getStructureBlock().getStructure()));

        return jokeBlockMap.entrySet().stream().map(entrySet -> {
            List<JokeBlockDto> jokeBlocksDto = entrySet.getValue().stream()
                    .map(jokeBlock -> jokeBlockMapper.jokeBlockToJokeBlockDto(jokeBlock))
                    .collect(Collectors.toList());

            return JokeBlocksAndStructureDto.builder()
                    .structureName(entrySet.getKey().getName())
                    .jokeBlocksDto(jokeBlocksDto)
                    .build();
        }).collect(Collectors.toList());
    }
}
