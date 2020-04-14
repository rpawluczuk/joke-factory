package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import springapp.jokefactory.entity.Author;

@CrossOrigin("http://localhost:4200")
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
