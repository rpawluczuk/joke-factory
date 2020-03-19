package springapp.jokefactory.domain.repository;

import org.springframework.data.repository.CrudRepository;
import springapp.jokefactory.domain.Joke;

public interface JokeRepository extends CrudRepository<Joke, Integer> {

}
