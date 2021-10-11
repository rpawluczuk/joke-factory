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
            "WHERE j.connectingTopic.id = :topicId OR j.comedyTopic.id = :topicId OR j.ostensibleTopic.id = :topicId")
    List<Joke> findAllJokesConnectedWithTopic(@Param("topicId") Long topicId);
}
