package springapp.jokefactory.algorithm.jokediagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.algorithm.AlgorithmFacade;
import springapp.jokefactory.algorithm.jokediagram.dto.JokeBlockDto;

import java.util.stream.Collectors;

@Service
class JokeDiagramService {

    @Autowired
    private JokeDiagramRepository jokeDiagramRepository;

    @Autowired
    private JokeDiagramMapper jokeDiagramMapper;

    @Autowired
    private AlgorithmFacade algorithmFacade;

    Iterable<JokeBlockDto> getJokeDiagram(Long jokeId, long algorithmId) {
        return jokeDiagramRepository.findBlocksByJoke(jokeId).stream()
                .filter(jokeBlock -> jokeBlock.getDiagramBlock().getAlgorithm().getId() == algorithmId)
                .map(jokeDiagramMapper::mapJokeBlockToDto)
                .collect(Collectors.toList());
    }

    Iterable<JokeBlockDto> getJokeDiagram(long algorithmId) {
        Algorithm algorithm = algorithmFacade.getAlgorithmById(algorithmId);
        return algorithm.getDiagramBlockList().stream()
                .map(jokeDiagramMapper::mapDiagramBlockToJokeBlockDto)
                .collect(Collectors.toList());
    }
}
