//package springapp.jokefactory.domain.repository;
//
//import springapp.jokefactory.domain.Joke;
//import springapp.jokefactory.utils.Ids;
//
//import java.util.*;
//
//
//public class InMemoryJokeRepository {
//
//    Map<Integer, Joke> jokes = new HashMap<>();
//
//    public InMemoryJokeRepository() {
//    }
//
//
//    public void createJoke(String title, String content) {
//        Joke newJoke = new Joke(title, content);
//        newJoke.setId(Ids.generateNewId(jokes.keySet()));
//        jokes.put(newJoke.getId(), newJoke);
//    }
//
//
//    public void saveJoke(Joke newJoke) {
//        if (newJoke.getId() == null) {
//            newJoke.setId(Ids.generateNewId(jokes.keySet()));
//        }
//        jokes.put(newJoke.getId(), newJoke);
//    }
//
//
//    public Collection<Joke> getAllJokes() {
//        return jokes.values();
//    }
//
//
//    public Optional<Joke> getJoke(String title) {
//        Optional<Joke> jokeByName = jokes.values().stream().filter(joke -> joke.getTitle().equals(title)).findAny();
//        return jokeByName;
//    }
//
//
//    public void deleteJoke(Joke joke) {
//        jokes.remove(joke.getId());
//    }
//
//
//    public Joke getJokeById(Integer id) {
//        return jokes.get(id);
//    }
//
//    @Override
//    public String toString() {
//        return "JokeRepository{" +
//                "jokes=" + jokes +
//                '}';
//    }
//}
