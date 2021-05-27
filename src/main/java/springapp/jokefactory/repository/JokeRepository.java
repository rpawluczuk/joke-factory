package springapp.jokefactory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import springapp.jokefactory.entity.Author;
import springapp.jokefactory.entity.Joke;

public interface JokeRepository extends
                                JpaRepository<Joke, Long>,
                                QuerydslPredicateExecutor<Joke> {

    @Query
    Page<Joke> findJokesByAuthor(Author author, Pageable pageable);
}
