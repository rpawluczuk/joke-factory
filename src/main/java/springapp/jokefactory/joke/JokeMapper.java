package springapp.jokefactory.joke;

import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.dto.JokePresenterDto;

import java.text.SimpleDateFormat;

@Service
class JokeMapper {

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
}
