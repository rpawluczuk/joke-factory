package springapp.jokefactory.algorithm.jokediagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.jokediagram.dto.JokeBlockDto;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JokeDiagramFacade {

    @Autowired
    private JokeDiagramRepository jokeDiagramRepository;

    @Autowired
    private JokeDiagramMapper jokeDiagramMapper;

    public List<JokeBlock> extractJokeBlockList(List<JokeBlockDto> jokeBlockDtoList, Joke joke) {
        return Optional.ofNullable(jokeBlockDtoList).orElse(Collections.emptyList()).stream()
                .map(jokeBlockDto -> jokeDiagramMapper.mapDtoToJokeBlock(jokeBlockDto, joke))
                .collect(Collectors.toList());
    }

    public void saveJokeBlockList(List<JokeBlock> jokeBlockList) {
        jokeDiagramRepository.saveAll(jokeBlockList);
    }

    public List<JokeBlockDto> getJokeDiagram(long jokeId) {
        return jokeDiagramRepository.findBlocksByJoke(jokeId).stream()
                .map(jokeDiagramMapper::mapJokeBlockToDto)
                .collect(Collectors.toList());
    }
}
