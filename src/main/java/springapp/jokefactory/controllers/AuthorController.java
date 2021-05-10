package springapp.jokefactory.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import springapp.jokefactory.entity.Author;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.repository.AuthorRepository;
import springapp.jokefactory.repository.JokeRepository;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin("http://localhost:4200")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    JokeRepository jokeRepository;

    @GetMapping
    public Iterable<Author> getAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Author> getAuthorById(@PathVariable("id") Long id){
        return authorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@RequestBody Author author){
        authorRepository.save(author);
    }

    @PutMapping
    public void editAuthor(@RequestBody Author author){
        authorRepository.save(author);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAuthor(@PathVariable("id") Long id){
        Author authorToDelete = authorRepository.findById(id).get();
        Set<Joke> jokes = authorToDelete.getJokes();
        for (Joke joke: jokes) {
            joke.setAuthor(null);
            jokeRepository.save(joke);
        }
        authorRepository.delete(authorToDelete);
    }
}
