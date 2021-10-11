package springapp.jokefactory.joke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.topic.Topic;
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

    public void removeTopicFromJokes(Topic topic) {
        topic.getJokes().forEach(joke -> {
            joke.setConnectingTopic(null);
            jokeRepository.save(joke);
        });
        topic.getJokesAsOstensibleContext().forEach(joke -> {
            joke.setOstensibleTopic(null);
            jokeRepository.save(joke);
        });
        topic.getJokesAsComedyContext().forEach(joke -> {
            joke.setComedyTopic(null);
            jokeRepository.save(joke);
        });
    }
}
