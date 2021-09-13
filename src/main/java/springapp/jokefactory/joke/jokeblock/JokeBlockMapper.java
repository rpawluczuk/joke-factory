package springapp.jokefactory.joke.jokeblock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.structureblock.StructureBlock;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
interface JokeBlockMapper {

    @Mapping(target = "title", source = "jokeBlock.structureBlock.title")
    @Mapping(target = "description", source = "jokeBlock.structureBlock.description")
    @Mapping(target = "position", source = "jokeBlock.structureBlock.position")
    @Mapping(target = "structureBlockId", source = "jokeBlock.structureBlock.id")
    @Mapping(target = "structureName", source = "jokeBlock.structureBlock.structure.name")
    JokeBlockDto jokeBlockToJokeBlockDto(JokeBlock jokeBlock);

    @Mapping(target = "structureName", source = "structureBlock.structure.name")
    @Mapping(target = "structureBlockId", source = "structureBlock.id")
    @Mapping(target = "id", ignore = true)
    JokeBlockDto structureBlockToJokeBlockDto(StructureBlock structureBlock);

    default List<JokeBlocksAndStructureDto> hashMapToJokeBlocksAndStructureDto(HashMap<Structure, List<JokeBlockDto>> jokeBlockMap) {
        return jokeBlockMap.entrySet().stream()
                .map(entrySet -> new JokeBlocksAndStructureDto(entrySet.getKey().getName(), entrySet.getValue()))
                .collect(Collectors.toList());
    }
}
