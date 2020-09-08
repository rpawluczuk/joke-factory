package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
