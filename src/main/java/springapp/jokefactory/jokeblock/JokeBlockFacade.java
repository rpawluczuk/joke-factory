package springapp.jokefactory.jokeblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JokeBlockFacade {

    @Autowired
    private JokeBlockRepository jokeBlockRepository;

    @Autowired
    private JokeBlockMapper jokeBlockMapper;

    public List<JokeBlock> extractJokeBlockList(List<JokeBlockCreatorDto> jokeBlockCreatorDtoList, Joke joke) {
        return jokeBlockCreatorDtoList.stream()
                .map(jokeBlockCreatorDto -> jokeBlockMapper.jokeBlockCreatorDtoToJokeBlock(jokeBlockCreatorDto, joke))
                .collect(Collectors.toList());
    }

    public void saveJokeBlockList(List<JokeBlock> jokeBlockList) {
        jokeBlockList.forEach(jokeBlockRepository::save);
    }
}
