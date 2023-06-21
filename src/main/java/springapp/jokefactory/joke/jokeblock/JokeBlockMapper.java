package springapp.jokefactory.joke.jokeblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.algorithmblock.AlgorithmBlock;
import springapp.jokefactory.algorithm.algorithmblock.AlgorithmBlockFacade;
import springapp.jokefactory.joke.jokeblock.dto.JokeBlockDto;
import springapp.jokefactory.joke.Joke;

@Service
class JokeBlockMapper {

    @Autowired
    AlgorithmBlockFacade algorithmFacade;

    JokeBlockDto mapJokeBlockToDto(JokeBlock jokeBlock) {
        return JokeBlockDto.builder()
                .id(jokeBlock.getId())
                .jokeSnippet(jokeBlock.getJokeSnippet())
                .title(jokeBlock.getAlgorithmBlock().getTitle())
                .description(jokeBlock.getAlgorithmBlock().getDescription())
                .position(jokeBlock.getAlgorithmBlock().getPosition())
                .algorithmId(jokeBlock.getAlgorithmBlock().getAlgorithm().getId())
                .build();
    }

    JokeBlockDto mapDiagramBlockToJokeBlockDto(AlgorithmBlock algorithmBlock) {
        return JokeBlockDto.builder()
                .title(algorithmBlock.getTitle())
                .description(algorithmBlock.getDescription())
                .position(algorithmBlock.getPosition())
                .algorithmId(algorithmBlock.getAlgorithm().getId())
                .build();
    }

    JokeBlock mapDtoToJokeBlock(JokeBlockDto jokeBlockDto, Joke joke) {
        AlgorithmBlock algorithmBlock = algorithmFacade.getAlgorithmBlock(jokeBlockDto.getAlgorithmId(), jokeBlockDto.getPosition());
        return JokeBlock.builder()
                .id(jokeBlockDto.getId())
                .jokeSnippet(jokeBlockDto.getJokeSnippet())
                .joke(joke)
                .algorithmBlock(algorithmBlock)
                .build();
    }

}