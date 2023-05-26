package springapp.jokefactory.algorithm.jokediagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.diagram.DiagramBlock;
import springapp.jokefactory.algorithm.diagram.DiagramFacade;
import springapp.jokefactory.algorithm.jokediagram.dto.JokeBlockDto;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;

@Service
class JokeDiagramMapper {

    @Autowired
    DiagramFacade diagramFacade;

    JokeBlockDto mapJokeBlockToDto(JokeBlock jokeBlock) {
        return JokeBlockDto.builder()
                .id(jokeBlock.getId())
                .jokeSnippet(jokeBlock.getJokeSnippet())
                .title(jokeBlock.getDiagramBlock().getTitle())
                .description(jokeBlock.getDiagramBlock().getDescription())
                .position(jokeBlock.getDiagramBlock().getPosition())
                .algorithmId(jokeBlock.getDiagramBlock().getId())
                .build();
    }

    JokeBlockDto mapDiagramBlockToJokeBlockDto(DiagramBlock diagramBlock) {
        return JokeBlockDto.builder()
                .title(diagramBlock.getTitle())
                .description(diagramBlock.getDescription())
                .position(diagramBlock.getPosition())
                .algorithmId(diagramBlock.getAlgorithm().getId())
                .build();
    }

    JokeBlock mapDtoToJokeBlock(JokeBlockDto jokeBlockDto, Joke joke) {
        DiagramBlock diagramBlock = diagramFacade.getDiagramBlock(jokeBlockDto.getAlgorithmId(), jokeBlockDto.getPosition());
        return JokeBlock.builder()
                .id(jokeBlockDto.getId())
                .jokeSnippet(jokeBlockDto.getJokeSnippet())
                .joke(joke)
                .diagramBlock(diagramBlock)
                .build();
    }

//    @Mapping(target = "structureBlockPresenterDto", source = "jokeBlock.structureBlock")
//    @Mapping(target = "structureId", source = "jokeBlock.structureBlock.structure.id")
//    abstract JokeBlockCreatorDto jokeBlockToJokeBlockCreatorDto(JokeBlock jokeBlock);
//
//    @Mapping(target = "structureId", source = "structureBlock.structure.id")
//    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
//    @Mapping(target = "id", ignore = true)
//    abstract JokeBlockCreatorDto structureBlockToJokeBlockDto(DiagramBlock structureBlock);

//    @Mapping(target = "id", source = "jokeBlockCreatorDto.id")
//    @Mapping(target = "structureBlock", source = "structureBlock")
//    @Mapping(target = "dateCreated", ignore = true)
//    @Mapping(target = "lastUpdated", ignore = true)
//    abstract JokeBlock jokeBlockCreatorDtoToJokeBlock(JokeBlockCreatorDto jokeBlockCreatorDto, DiagramBlock structureBlock);
//
//    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
//    @Mapping(target = "structureId", source = "structureBlock.structure.id")
//    abstract JokeBlockPresenterDto jokeBlockToJokeBlockPresenterDto(JokeBlock jokeBlock);
}
