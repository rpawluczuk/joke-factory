package springapp.jokefactory.joke.jokeblock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.structure.structureblock.StructureBlock;

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
}
