package springapp.jokefactory.domain.repository;

import springapp.jokefactory.domain.Joke;

import java.util.Collection;
import java.util.Optional;

public interface JokeRepository {

    void createJoke(String title, String content);

    Collection<Joke> getAllJokes();

    Optional<Joke> getJoke(String title);

    void deleteJoke(Integer id);

    void createJoke(Joke joke);

    Joke getJokeById(Integer id);
}
