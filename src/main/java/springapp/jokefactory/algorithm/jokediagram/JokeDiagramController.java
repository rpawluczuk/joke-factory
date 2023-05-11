package springapp.jokefactory.algorithm.jokediagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.algorithm.jokediagram.dto.JokeBlockDto;

@RestController
@RequestMapping("/api/joke-diagram")
@CrossOrigin("http://localhost:3000")
class JokeDiagramController {

    @Autowired
    private JokeDiagramService jokeDiagramService;


    @GetMapping(value = "/{joke_id}/{algorithm_number}")
    Iterable<JokeBlockDto> getJokeDiagram(@PathVariable("joke_id") Long jokeId,
                                          @PathVariable("algorithm_number") int algorithmNumber){
        return jokeDiagramService.getJokeDiagram(jokeId, algorithmNumber);
    }
}
