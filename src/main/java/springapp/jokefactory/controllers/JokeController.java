package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/jokes")
@CrossOrigin("http://localhost:4200")
public class JokeController {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    @GetMapping
    public Iterable<Joke> getJokes(){
        return jokeRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Joke> getJokeById(@PathVariable("id") Long id){
        return jokeRepository.findById(id);
    }

    @PostMapping
    public void addJoke(@RequestBody Joke joke){
        jokeRepository.save(joke);
    }

    @PutMapping(value = "/{id}")
    public void editJoke(@PathVariable("id") Long id, @RequestBody Joke joke){
        Joke jokeToUpdate = jokeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Joke not found for this id :: " + id));
        jokeToUpdate.setTitle(joke.getTitle());
        jokeToUpdate.setContent(joke.getContent());
        jokeRepository.save(joke);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteJoke(@PathVariable("id") Long id){
        Joke jokeToDelete = jokeRepository.findById(id).get();
        jokeRepository.delete(jokeToDelete);
    }
}
