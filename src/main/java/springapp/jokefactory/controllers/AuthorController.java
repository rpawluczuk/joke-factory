package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.entity.Author;
import springapp.jokefactory.repository.AuthorRepository;
import springapp.jokefactory.repository.JokeRepository;

import java.util.Optional;

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
        authorRepository.delete(authorToDelete);
    }
}
