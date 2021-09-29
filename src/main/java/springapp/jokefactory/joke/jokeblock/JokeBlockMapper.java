package springapp.jokefactory.joke.jokeblock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.structure.structureblock.StructureBlock;

@Mapper
public
interface JokeBlockMapper {

    @Mapping(target = "title", source = "jokeBlock.structureBlock.title")
    @Mapping(target = "description", source = "jokeBlock.structureBlock.description")
    @Mapping(target = "position", source = "jokeBlock.structureBlock.position")
    @Mapping(target = "structureBlockId", source = "jokeBlock.structureBlock.id")
    @Mapping(target = "structureId", source = "jokeBlock.structureBlock.structure.id")
    JokeBlockDto jokeBlockToJokeBlockDto(JokeBlock jokeBlock);

    @Mapping(target = "structureId", source = "structureBlock.structure.id")
    @Mapping(target = "structureBlockId", source = "structureBlock.id")
    @Mapping(target = "id", ignore = true)
    JokeBlockDto structureBlockToJokeBlockDto(StructureBlock structureBlock);

    @Mapping(target = "id", source = "jokeBlockDto.id")
    @Mapping(target = "joke", source = "joke")
    @Mapping(target = "structureBlock", source = "structureBlock")
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    JokeBlock jokeBlockDtoToJokeBlock(JokeBlockDto jokeBlockDto, Joke joke, StructureBlock structureBlock);
}
