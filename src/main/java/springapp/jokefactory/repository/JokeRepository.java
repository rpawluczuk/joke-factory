package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import springapp.jokefactory.entity.Joke;

public interface JokeRepository extends
                                JpaRepository<Joke, Long>,
                                QuerydslPredicateExecutor<Joke> {

    @Query(value = "SELECT max(id) FROM Joke")
    long findHighestID();
}
