package springapp.jokefactory.joke;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.joke.dto.JokePresenterDto;
import springapp.jokefactory.joke.dto.JokeRateDto;

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

    @GetMapping(value = "/creator/{id}")
    JokeCreatorDto getJokeCreatorById(@PathVariable("id") Long id) {
        return jokeService.getJokeCreatorById(id);
    }

    @GetMapping(value = "/presenter/{id}")
    JokePresenterDto getJokePresenterById(@PathVariable("id") Long id) {
        return jokeService.getJokePresenterById(id);
    }

    @GetMapping(value = "/pagination")
    public JokePagination getPagination(){
        return jokeService.getJokePagination();
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

    @PutMapping(value = "/pagination")
    public void updatePagination(@RequestBody JokePagination jokePagination){
        this.jokeService.updatePagination(jokePagination);
    }

    @DeleteMapping(value = "/{id}")
    void deleteJoke(@PathVariable("id") Long id) {
        jokeService.deleteJoke(id);
    }

    @PatchMapping(value = "/rate")
    void rateJoke(@RequestBody JokeRateDto jokeRateDto){
        jokeService.rateJoke(jokeRateDto);
    }

    @DeleteMapping(value = "/reset-rate/{id}")
    void resetJokeRate(@PathVariable("id") Long id) {
        jokeService.resetJokeRate(id);
    }
}
