package springapp.jokefactory.joke;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.joke.dto.JokePresenterDto;

@RestController
@RequestMapping("/api/jokes")
@CrossOrigin("http://localhost:4200")
class JokeController {

    @Autowired
    JokeService jokeService;

    @GetMapping()
    Iterable<JokePresenterDto> getJokePresenterList() {
        return jokeService.getJokePresenterList();
    }

    @GetMapping(params = "query")
    Iterable<JokePresenterDto> getFilteredJokes(@QuerydslPredicate(root = Joke.class) Predicate predicate) {
        return jokeService.getFilteredJokePresenterList(predicate);
    }

    @GetMapping(value = "/{id}")
    JokeCreatorDto getJokeById(@PathVariable("id") Long id) {
        return jokeService.getJokeCreatorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addJoke(@RequestBody JokeCreatorDto jokeCreatorDTO) {
        jokeService.addJoke(jokeCreatorDTO);
    }

    @PutMapping
    void editJoke(@RequestBody JokeCreatorDto jokeCreatorDTO) {
        jokeService.editJoke(jokeCreatorDTO);
    }

    @DeleteMapping(value = "/{id}")
    void deleteJoke(@PathVariable("id") Long id) {
        jokeService.deleteJoke(id);
    }
}
