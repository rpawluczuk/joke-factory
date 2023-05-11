package springapp.jokefactory.algorithm.jokediagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.jokediagram.dto.JokeBlockDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JokeDiagramFacade {

    @Autowired
    private JokeDiagramRepository jokeDiagramRepository;

    @Autowired
    private JokeDiagramMapper jokeDiagramMapper;

//    @Autowired
//    private JokeBlockMapper jokeBlockMapper;
//
//    public List<JokeBlock> extractJokeBlockList(List<JokeBlockCreatorDto> jokeBlockCreatorDtoList, Joke joke) {
//        return Optional.ofNullable(jokeBlockCreatorDtoList).orElse(Collections.emptyList()).stream()
//                .map(jokeBlockCreatorDto -> jokeBlockMapper.jokeBlockCreatorDtoToJokeBlock(jokeBlockCreatorDto, joke))
//                .collect(Collectors.toList());
//    }
//
//    public void saveJokeBlockList(List<JokeBlock> jokeBlockList) {
//        jokeBlockList.forEach(jokeBlockRepository::save);
//    }

    public List<JokeBlockDto> getJokeDiagram(long jokeId) {
        return jokeDiagramRepository.findBlocksByJoke(jokeId).stream()
                .map(jokeDiagramMapper::mapJokeBlockToDto)
                .collect(Collectors.toList());
    }
}
