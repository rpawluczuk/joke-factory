package springapp.jokefactory.joke;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JokeRepository extends JpaRepository<Joke, Long>,
                                QuerydslPredicateExecutor<Joke> {

    @Query(value = "SELECT MAX(id) FROM Joke")
    long findHighestID();
}
