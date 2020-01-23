package springapp.jokefactory.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.services.StructureService;
import springapp.jokefactory.utils.Ids;

import java.util.*;


public class InMemoryJokeRepository implements JokeRepository {

    Map<Integer, Joke> jokes = new HashMap<>();

    @Autowired
    StructureService structureService;

    public InMemoryJokeRepository() {
    }

    @Override
    public void createJoke(String title, String content) {
        Joke newJoke = new Joke(title, content);
        newJoke.setId(Ids.generateNewId(jokes.keySet()));
        jokes.put(newJoke.getId(), newJoke);
    }

    @Override
    public void createJoke(Joke newJoke) {
        newJoke.setId(Ids.generateNewId(jokes.keySet()));
        jokes.put(newJoke.getId(), newJoke);
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
    public Joke getJokeById(Integer id) {
        return jokes.get(id);
    }

    @Override
    public Joke assignStructure(Joke joke) {
        List<Structure> allStructures = structureService.getAllStructures();
        for (Structure structure : allStructures) {
            if (joke.getStructureId().equals(structure.getId())){
                joke.setStructure(structure);
                return joke;
            }
        }
        return joke;
    }

    @Override
    public String toString() {
        return "JokeRepository{" +
                "jokes=" + jokes +
                '}';
    }
}
