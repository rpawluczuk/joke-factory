package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Joke;

import java.util.*;


public class InMemoryJokeRepository implements JokeRepository {

    Map<Integer, Joke> jokes = new HashMap<>();

    public InMemoryJokeRepository() {
    }

    @Override
    public void createJoke(String title, String content) {
        Joke newJoke = new Joke(title, content);
        newJoke.setId(getNewId());
        jokes.put(newJoke.getId(), newJoke);
    }

    private int getNewId() {
        if (jokes.isEmpty()) {
            return 0;
        }
        else{
            return Collections.max(jokes.keySet()) + 1;
        }
    }

    @Override
    public Collection<Joke> getAllJokes() {
        return jokes.values();
    }

    @Override
    public Optional<Joke> getJoke(String title) {
        Optional<Joke> jokeByName = jokes.values().stream().filter(joke -> joke.getTitle().equals(title)).findAny();
        return jokeByName;
    }

    @Override
    public void deleteJoke(Integer id) {
        jokes.remove(id);
    }

    @Override
    public void createJoke(Joke newJoke) {
        newJoke.setId(getNewId());
        jokes.put(newJoke.getId(), newJoke);
    }

    @Override
    public Joke getJokeById(Integer id) {
        return jokes.get(id);
    }

    @Override
    public String toString() {
        return "JokeRepository{" +
                "jokes=" + jokes +
                '}';
    }
}
