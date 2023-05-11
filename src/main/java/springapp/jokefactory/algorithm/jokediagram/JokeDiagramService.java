package springapp.jokefactory.algorithm.jokediagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.jokediagram.dto.JokeBlockDto;

import java.util.stream.Collectors;

@Service
class JokeDiagramService {

    @Autowired
    private JokeDiagramRepository jokeDiagramRepository;

    @Autowired
    private JokeDiagramMapper jokeDiagramMapper;

    Iterable<JokeBlockDto> getJokeDiagram(Long jokeId, long algorithmId) {
        return jokeDiagramRepository.findBlocksByJoke(jokeId).stream()
                .filter(jokeBlock -> jokeBlock.getDiagramBlock().getAlgorithm().getId() == algorithmId)
                .map(jokeDiagramMapper::mapJokeBlockToDto)
                .collect(Collectors.toList());
    }
}
