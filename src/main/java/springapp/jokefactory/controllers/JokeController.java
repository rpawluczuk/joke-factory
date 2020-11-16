package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

import java.util.*;

@RestController
@RequestMapping("/api/jokes")
@CrossOrigin("http://localhost:4200")
public class JokeController {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getJokes(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size){
        try {
            List<Joke> jokes;
            Pageable paging = PageRequest.of(page, size);
            Page<Joke> pageJokes;
            pageJokes = jokeRepository.findAll(paging);
            jokes = pageJokes.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("jokes", jokes);
            response.put("currentPage", pageJokes.getNumber());
            response.put("totalItems", pageJokes.getTotalElements());
            response.put("totalPages", pageJokes.getTotalPages());
            response.put("pageSize", pageJokes.getSize());
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
        jokeRepository.save(jokeToUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteJoke(@PathVariable("id") Long id){
        Joke jokeToDelete = jokeRepository.findById(id).get();
        jokeRepository.delete(jokeToDelete);
    }
}
