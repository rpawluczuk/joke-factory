package springapp.jokefactory.joke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.author.Author;
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

    public Joke getJokeById(Long id) {
        return jokeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No joke found with id: " + id));
    }
}
