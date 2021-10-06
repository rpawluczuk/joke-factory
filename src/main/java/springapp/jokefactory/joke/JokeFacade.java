package springapp.jokefactory.joke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.origin.Origin;
import springapp.jokefactory.structure.Structure;

@Service
public class JokeFacade {

    @Autowired
    private JokeRepository jokeRepository;

    public void removeAuthorFromJokes(Author author) {
        author.getJokes().forEach(joke -> {
            joke.setAuthor(null);
            jokeRepository.save(joke);
        });
    }

    public void removeStructureFromJokes(Structure structure) {
        structure.getJokes().forEach(joke -> {
            joke.getStructures().remove(structure);
            jokeRepository.save(joke);
        });
    }

    public void removeOriginFromJokes(Origin origin) {
        origin.getJokes().forEach(joke -> {
            joke.setOrigin(null);
            jokeRepository.save(joke);
        });
        origin.getJokesAsOstensibleContext().forEach(joke -> {
            joke.setOstensibleOrigin(null);
            jokeRepository.save(joke);
        });
        origin.getJokesAsComedyContext().forEach(joke -> {
            joke.setComedyOrigin(null);
            jokeRepository.save(joke);
        });
    }
}
