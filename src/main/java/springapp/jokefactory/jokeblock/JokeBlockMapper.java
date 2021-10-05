package springapp.jokefactory.jokeblock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
import springapp.jokefactory.jokeblock.dto.JokeBlockPresenterDto;
import springapp.jokefactory.structureblock.StructureBlock;
import springapp.jokefactory.structureblock.StructureBlockMapper;
import springapp.jokefactory.structureblock.dto.StructureBlockPresenterDto;

@Mapper
public
interface JokeBlockMapper {

    StructureBlockMapper structureBlockMapper = Mappers.getMapper(StructureBlockMapper.class);

    @Mapping(target = "structureBlockPresenterDto", source = "jokeBlock.structureBlock")
    @Mapping(target = "structureId", source = "jokeBlock.structureBlock.structure.id")
    JokeBlockCreatorDto jokeBlockToJokeBlockCreatorDto(JokeBlock jokeBlock);

    @Mapping(target = "structureId", source = "structureBlock.structure.id")
    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
    @Mapping(target = "id", ignore = true)
    JokeBlockCreatorDto structureBlockToJokeBlockDto(StructureBlock structureBlock);

    @Mapping(target = "id", source = "jokeBlockCreatorDto.id")
    @Mapping(target = "joke", source = "joke")
    @Mapping(target = "structureBlock", source = "structureBlock")
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    JokeBlock jokeBlockCreatorDtoToJokeBlock(JokeBlockCreatorDto jokeBlockCreatorDto, Joke joke, StructureBlock structureBlock);

    @Mapping(target = "id", source = "jokeBlockCreatorDto.id")
    @Mapping(target = "structureBlock", source = "structureBlock")
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    JokeBlock jokeBlockCreatorDtoToJokeBlock(JokeBlockCreatorDto jokeBlockCreatorDto, StructureBlock structureBlock);

    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
    @Mapping(target = "structureId", source = "structureBlock.structure.id")
    JokeBlockPresenterDto jokeBlockToJokeBlockPresenterDto(JokeBlock jokeBlock);

    @Named("extractStructureBlockPresenterDto")
    default StructureBlockPresenterDto extractStructureBlockPresenterDto(StructureBlock structureBlock) {
        return structureBlockMapper.mapStructureBlockToStructureBlockPresenterDto(structureBlock);
    }
}
