package springapp.jokefactory.joke;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JokeRepository extends
                                JpaRepository<Joke, Long>,
                                QuerydslPredicateExecutor<Joke> {

    @Query(value = "SELECT MAX(id) FROM Joke")
    long findHighestID();

    @Query(value = "SELECT j FROM Joke j " +
            "WHERE j.origin.id = :originId OR j.comedyOrigin.id = :originId OR j.ostensibleOrigin.id = :originId")
    List<Joke> findAllJokesConnectedWithOrigin(@Param("originId") Long originId);
}
