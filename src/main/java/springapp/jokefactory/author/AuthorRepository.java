package springapp.jokefactory.author;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.author.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
