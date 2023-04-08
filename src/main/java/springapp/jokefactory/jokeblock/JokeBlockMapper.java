package springapp.jokefactory.jokeblock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.algorithm.diagram.DiagramBlock;
import springapp.jokefactory.algorithm.diagram.DiagramBlockPresenterDto;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
import springapp.jokefactory.jokeblock.dto.JokeBlockPresenterDto;
import springapp.jokefactory.algorithm.diagram.DiagramFacade;

//@Mapper(componentModel = "spring")
//abstract class JokeBlockMapper {
//
//    @Autowired
//    protected DiagramFacade structureBlockFacade;
//
//    @Mapping(target = "structureBlockPresenterDto", source = "jokeBlock.structureBlock")
//    @Mapping(target = "structureId", source = "jokeBlock.structureBlock.structure.id")
//    abstract JokeBlockCreatorDto jokeBlockToJokeBlockCreatorDto(JokeBlock jokeBlock);
//
//    @Mapping(target = "structureId", source = "structureBlock.structure.id")
//    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
//    @Mapping(target = "id", ignore = true)
//    abstract JokeBlockCreatorDto structureBlockToJokeBlockDto(DiagramBlock structureBlock);
//
//    @Mapping(target = "id", source = "jokeBlockCreatorDto.id")
//    @Mapping(target = "joke", source = "joke")
//    @Mapping(target = "structureBlock", source = "jokeBlockCreatorDto.structureBlockPresenterDto", qualifiedByName = "extractStructureBlock")
//    @Mapping(target = "dateCreated", ignore = true)
//    @Mapping(target = "lastUpdated", ignore = true)
//    abstract JokeBlock jokeBlockCreatorDtoToJokeBlock(JokeBlockCreatorDto jokeBlockCreatorDto, Joke joke);
//
//    @Mapping(target = "id", source = "jokeBlockCreatorDto.id")
//    @Mapping(target = "structureBlock", source = "structureBlock")
//    @Mapping(target = "dateCreated", ignore = true)
//    @Mapping(target = "lastUpdated", ignore = true)
//    abstract JokeBlock jokeBlockCreatorDtoToJokeBlock(JokeBlockCreatorDto jokeBlockCreatorDto, DiagramBlock structureBlock);
//
//    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
//    @Mapping(target = "structureId", source = "structureBlock.structure.id")
//    abstract JokeBlockPresenterDto jokeBlockToJokeBlockPresenterDto(JokeBlock jokeBlock);

//    @Named("extractStructureBlockPresenterDto")
//    DiagramBlockPresenterDto extractStructureBlockPresenterDto(DiagramBlock structureBlock) {
//        return structureBlockFacade.extractStructureBlockPresenterDto(structureBlock);
//    }
//
//    @Named("extractStructureBlock")
//    DiagramBlock extractStructureBlock(DiagramBlockPresenterDto structureBlockPresenterDto) {
//        return structureBlockFacade.tryToGetStructureBlockByID(structureBlockPresenterDto.getId());
//    }
//}
