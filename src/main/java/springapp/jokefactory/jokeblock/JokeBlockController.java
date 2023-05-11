package springapp.jokefactory.jokeblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/joke-blocks")
@CrossOrigin("http://localhost:4200")
class JokeBlockController {

    @Autowired
    JokeBlockService jokeBlockService;

//    @GetMapping(value = "creator-list/{joke_id}")
//    Iterable<JokeBlockCreatorDto> getBlocksOfTheJoke(@PathVariable("joke_id") Long jokeId) {
//        return jokeBlockService.getJokeBlockCreatorList(jokeId);
//    }
//
//    @GetMapping(params = "structureId")
//    List<JokeBlockCreatorDto> getJokeBlockCreatorListOfStructure(@RequestParam("structureId") Long structureId) {
//        return jokeBlockService.getJokeBlockCreatorListOfStructure(structureId);
//    }
}
