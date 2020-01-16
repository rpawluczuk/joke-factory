package springapp.jokefactory.domain.repository;

import springapp.jokefactory.domain.Joke;

import java.util.Collection;

public interface JokeRepository {

    void createJoke(String title, String content);

    Collection<Joke> getAllJokes();

    Joke getJoke(String title);

    void deleteJoke(String title);
}
