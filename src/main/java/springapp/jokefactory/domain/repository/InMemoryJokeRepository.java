package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Joke;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class InMemoryJokeRepository implements JokeRepository {

    Map<String, Joke> jokes = new HashMap<>();

    public InMemoryJokeRepository(){}

    @Override
    public void createJoke(String title, String content) {
        jokes.put(title, new Joke(title, content));
    }

    @Override
    public Collection<Joke> getAllJokes() {
        return jokes.values();
    }

    @Override
    public Joke getJoke(String title) {
        return jokes.get(title);
    }

    @Override
    public void deleteJoke(String title){
        jokes.remove(title);
    }

    @Override
    public void createJoke(Joke joke) {
        jokes.put(joke.getTitle(), joke);
    }

    @Override
    public String toString() {
        return "JokeRepository{" +
                "jokes=" + jokes +
                '}';
    }
}
