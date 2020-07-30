package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.entity.Joke;

public interface JokeRepository extends JpaRepository<Joke, Long> {

}
