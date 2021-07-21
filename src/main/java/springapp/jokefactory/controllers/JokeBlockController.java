package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.entity.JokeBlock;
import springapp.jokefactory.entity.StructureBlock;
import springapp.jokefactory.repository.JokeBlockRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/joke-blocks")
@CrossOrigin("http://localhost:4200")
public class JokeBlockController {

    @Autowired
    JokeBlockRepository jokeBlockRepository;

    @GetMapping
    public Iterable<JokeBlock> getJokeBlocks(){
        return jokeBlockRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<JokeBlock> getJokeBlockById(@PathVariable("id") Long id){
        return jokeBlockRepository.findById(id);
    }

    @GetMapping(value = "with-joke/{joke_id}")
    public Iterable<JokeBlock> getBlocksOfTheJoke(@PathVariable("joke_id") Long jokeID){
        return jokeBlockRepository.findBlocksByJoke(jokeID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addJokeBlock(@RequestBody JokeBlock jokeBlock){
        jokeBlockRepository.save(jokeBlock);
    }

    @PutMapping
    public void editJokeBlock(@RequestBody JokeBlock jokeBlock){
        jokeBlockRepository.save(jokeBlock);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteJokeBlock(@PathVariable("id") Long id){
        JokeBlock jokeBlockToDelete = jokeBlockRepository.findById(id).get();
        jokeBlockRepository.delete(jokeBlockToDelete);
    }
}
