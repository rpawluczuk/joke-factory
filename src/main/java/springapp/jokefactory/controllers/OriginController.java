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

import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.entity.Origin;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.OriginRepository;

@RestController
@RequestMapping("/api/origins")
@CrossOrigin("http://localhost:4200")
public class OriginController {

    @Autowired
    OriginRepository originRepository;

    @Autowired
    JokeRepository jokeRepository;

    @GetMapping
    public Iterable<Origin> getOrigins(){
        return originRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Origin> getOriginById(@PathVariable("id") Long id){
        return originRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrigin(@RequestBody Origin origin){
        originRepository.save(origin);
    }

    @PutMapping
    public void editOrigin(@RequestBody Origin origin){
        originRepository.save(origin);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrigin(@PathVariable("id") Long id){
        Origin originToDelete = originRepository.findById(id).get();
        Set<Joke> jokes = originToDelete.getJokes();
        for (Joke joke: jokes) {
            joke.setOrigin(null);
            jokeRepository.save(joke);
        }
        originRepository.delete(originToDelete);
    }
}
