package springapp.jokefactory.entity.repository;

import org.springframework.data.repository.CrudRepository;
import springapp.jokefactory.entity.Joke;

public interface JokeRepository extends CrudRepository<Joke, Integer> {

}
