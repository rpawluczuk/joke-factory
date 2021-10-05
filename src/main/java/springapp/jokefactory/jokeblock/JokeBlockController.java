package springapp.jokefactory.jokeblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
import springapp.jokefactory.jokeblock.dto.JokeBlockPresenterDto;

import java.util.List;

@RestController
@RequestMapping("/api/joke-blocks")
@CrossOrigin("http://localhost:4200")
class JokeBlockController {

    @Autowired
    JokeBlockService jokeBlockService;

    @GetMapping(value = "creator-list/{joke_id}")
    Iterable<JokeBlockCreatorDto> getBlocksOfTheJoke(@PathVariable("joke_id") Long jokeId) {
        return jokeBlockService.getJokeBlockCreatorList(jokeId);
    }

    @GetMapping(value = "presenter-list/{joke_id}")
    Iterable<JokeBlockPresenterDto> getJokeBlockPresenterList(@PathVariable("joke_id") Long jokeId) {
        return jokeBlockService.getJokeBlockPresenterList(jokeId);
    }

    @GetMapping(params = "structureId")
    List<JokeBlockCreatorDto> getJokeBlockCreatorListOfStructure(@RequestParam("structureId") Long structureId) {
        return jokeBlockService.getJokeBlockCreatorListOfStructure(structureId);
    }
}
