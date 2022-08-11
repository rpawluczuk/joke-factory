package springapp.jokefactory.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorFacade {

    @Autowired
    private AuthorRepository authorRepository;

    public Author tryToGetAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No author found with id: " + id));
    }
}
