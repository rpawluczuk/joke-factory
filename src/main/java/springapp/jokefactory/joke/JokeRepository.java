package springapp.jokefactory.joke;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface JokeRepository extends
                                JpaRepository<Joke, Long>,
                                QuerydslPredicateExecutor<Joke> {

    @Query(value = "SELECT max(id) FROM Joke")
    long findHighestID();
}
