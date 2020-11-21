package springapp.jokefactory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springapp.jokefactory.entity.Author;
import springapp.jokefactory.entity.Joke;

public interface JokeRepository extends JpaRepository<Joke, Long> {

    @Query
    Page<Joke> findJokesByAuthor(Author author, Pageable pageable);
}
