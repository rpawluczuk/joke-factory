package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import springapp.jokefactory.entity.Joke;

@CrossOrigin("http://localhost:4200")
public interface JokeRepository extends JpaRepository<Joke, Long> {

}
