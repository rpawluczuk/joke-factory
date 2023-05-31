package springapp.jokefactory.joke.jokeblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.jokeblock.dto.JokeBlockDto;
import springapp.jokefactory.joke.Joke;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JokeBlockFacade {

    @Autowired
    private JokeBlockRepository jokeBlockRepository;

    @Autowired
    private JokeBlockMapper jokeBlockMapper;

    public List<JokeBlock> extractJokeBlockList(List<JokeBlockDto> jokeBlockDtoList, Joke joke) {
        return Optional.ofNullable(jokeBlockDtoList).orElse(Collections.emptyList()).stream()
                .map(jokeBlockDto -> jokeBlockMapper.mapDtoToJokeBlock(jokeBlockDto, joke))
                .collect(Collectors.toList());
    }

    public void saveJokeBlockList(List<JokeBlock> jokeBlockList) {
        jokeBlockRepository.saveAll(jokeBlockList);
    }

    public List<JokeBlockDto> getJokeBlockDtoList(long jokeId) {
        return jokeBlockRepository.findBlocksByJoke(jokeId).stream()
                .map(jokeBlockMapper::mapJokeBlockToDto)
                .collect(Collectors.toList());
    }

    public void deleteAllJokeBlockByAlgorithmId(long algorithmId) {
        List<JokeBlock> jokeBlockListToDelete = jokeBlockRepository.findJokeBlocksByAlgorithmId(algorithmId);
        jokeBlockRepository.deleteAll(jokeBlockListToDelete);
    }
}
