package springapp.jokefactory.domain.repository;

import org.springframework.data.repository.CrudRepository;
import springapp.jokefactory.domain.Joke;

import java.util.Collection;
import java.util.Optional;

public interface JokeRepository extends CrudRepository<Joke, Integer> {

//    void createJoke(String title, String content);
//
//    Collection<Joke> getAllJokes();
//
//    Optional<Joke> getJoke(String title);
//
//    void deleteJoke(Joke joke);
//
//    void saveJoke(Joke joke);
//
//    Joke getJokeById(Integer id);
}
