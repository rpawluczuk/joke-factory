package springapp.jokefactory.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springapp.jokefactory.entity.Author;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.repository.AuthorRepository;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;
import springapp.jokefactory.utils.Pagination;

@RestController
@RequestMapping("/api/jokes")
@CrossOrigin("http://localhost:4200")
public class JokeController {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    StructureRepository structureRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getJokes(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @RequestParam(defaultValue = "-1") int authorFilter){
        try {
            List<Joke> jokes;
            Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "dateCreated");
            Page<Joke> pageJokes;
            if (authorFilter != -1){
                Optional<Author> author = authorRepository.findById((long) authorFilter);
                pageJokes = jokeRepository.findJokesByAuthor(author.get(), paging);
            } else {
                pageJokes = jokeRepository.findAll(paging);
            }
            jokes = pageJokes.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("jokes", jokes);
            Pagination pagination = new Pagination(
                    pageJokes.getNumber(),
                    pageJokes.getTotalElements(),
                    pageJokes.getTotalPages(),
                    pageJokes.getSize());
            response.put("pagination", pagination);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public Optional<Joke> getJokeById(@PathVariable("id") Long id){
        return jokeRepository.findById(id);
    }

    @PostMapping
    public void addJoke(@RequestBody Joke joke){
        System.out.println(joke.toString());
        jokeRepository.save(joke);
    }

    @PutMapping(value = "/{id}")
    public void editJoke(@PathVariable("id") Long id, @RequestBody Joke joke){
        Joke jokeToUpdate = jokeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Joke not found for this id :: " + id));
        jokeToUpdate.setTitle(joke.getTitle());
        jokeToUpdate.setContent(joke.getContent());
        jokeToUpdate.setStructures(joke.getStructures());
        jokeToUpdate.setAuthor(joke.getAuthor());
        jokeToUpdate.setOrigin(joke.getOrigin());
        jokeRepository.save(jokeToUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteJoke(@PathVariable("id") Long id){
        Joke jokeToDelete = jokeRepository.findById(id).get();
        jokeRepository.delete(jokeToDelete);
    }
}
