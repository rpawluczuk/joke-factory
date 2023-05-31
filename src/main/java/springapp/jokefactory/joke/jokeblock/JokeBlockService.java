package springapp.jokefactory.joke.jokeblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.algorithm.AlgorithmFacade;
import springapp.jokefactory.joke.jokeblock.dto.JokeBlockDto;

import java.util.stream.Collectors;

@Service
class JokeBlockService {

    @Autowired
    private JokeBlockRepository jokeDiagramRepository;

    @Autowired
    private JokeBlockMapper jokeDiagramMapper;

    @Autowired
    private AlgorithmFacade algorithmFacade;

    Iterable<JokeBlockDto> getJokeDiagram(Long jokeId, long algorithmId) {
        return jokeDiagramRepository.findJokeBlocksByJoke(jokeId).stream()
                .filter(jokeBlock -> jokeBlock.getAlgorithmBlock().getAlgorithm().getId() == algorithmId)
                .map(jokeDiagramMapper::mapJokeBlockToDto)
                .collect(Collectors.toList());
    }

    Iterable<JokeBlockDto> getJokeDiagram(long algorithmId) {
        Algorithm algorithm = algorithmFacade.getAlgorithmById(algorithmId);
        return algorithm.getAlgorithmBlockList().stream()
                .map(jokeDiagramMapper::mapDiagramBlockToJokeBlockDto)
                .collect(Collectors.toList());
    }
}
