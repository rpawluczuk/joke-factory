package springapp.jokefactory.joke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.dto.AlgorithmItemDto;
import springapp.jokefactory.joke.jokeblock.JokeBlockFacade;
import springapp.jokefactory.joke.jokeblock.dto.JokeBlockDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.joke.dto.JokePresenterDto;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
class JokeMapper {

    @Autowired
    JokeBlockFacade jokeBlockFacade;

    JokePresenterDto mapJokeToJokePresenterDto(Joke joke) {
        String author = joke.getAuthor().getName() + " " + joke.getAuthor().getSurname();
        String dateCreated = new SimpleDateFormat("yyyy-MM-dd").format(joke.getDateCreated());
        return JokePresenterDto.builder()
                .id(joke.getId())
                .title(joke.getTitle())
                .content(joke.getContent())
                .author(author)
                .dateCreated(dateCreated)
                .build();
    }

    JokeCreatorDto mapJokeToJokeCreatorDto(Joke joke) {
        String author = joke.getAuthor().getName() + " " + joke.getAuthor().getSurname();
        AuthorItemDto authorItemDto = new AuthorItemDto(author, joke.getAuthor().getId());
        List<AlgorithmItemDto> algorithmItemList = joke.getAlgorithms().stream()
                .map(algorithm -> new AlgorithmItemDto(algorithm.getName(), algorithm.getId()))
                .collect(Collectors.toList());
        List<JokeBlockDto> jokeBlockDtoList = jokeBlockFacade.getJokeBlockDtoList(joke.getId());
        return JokeCreatorDto.builder()
                .id(joke.getId())
                .title(joke.getTitle())
                .content(joke.getContent())
                .dateCreated(joke.getDateCreated())
                .authorItem(authorItemDto)
                .algorithmItemList(algorithmItemList)
                .jokeBlockDtoList(jokeBlockDtoList)
                .build();
    }
}
