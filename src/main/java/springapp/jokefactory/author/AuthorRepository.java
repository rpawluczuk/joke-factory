package springapp.jokefactory.author;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.author.Author;

interface AuthorRepository extends JpaRepository<Author, Long> {
}
