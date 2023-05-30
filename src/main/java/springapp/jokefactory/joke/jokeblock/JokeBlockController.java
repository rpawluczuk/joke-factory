package springapp.jokefactory.joke.jokeblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.joke.jokeblock.dto.JokeBlockDto;

@RestController
@RequestMapping("/api/joke-diagram")
@CrossOrigin("http://localhost:3000")
class JokeBlockController {

    @Autowired
    private JokeBlockService jokeBlockService;


    @GetMapping(value = "/{joke_id}/{algorithm_id}")
    Iterable<JokeBlockDto> getJokeDiagram(@PathVariable("joke_id") Long jokeId,
                                          @PathVariable("algorithm_id") int algorithmId){
        return jokeBlockService.getJokeDiagram(jokeId, algorithmId);
    }

    @GetMapping(value = "/{algorithm_id}")
    Iterable<JokeBlockDto> getJokeDiagram(@PathVariable("algorithm_id") int algorithmId){
        return jokeBlockService.getJokeDiagram(algorithmId);
    }
}
