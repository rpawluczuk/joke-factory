package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.entity.JokeBlock;

public interface JokeBlockRepository extends JpaRepository<JokeBlock, Long> {
}
