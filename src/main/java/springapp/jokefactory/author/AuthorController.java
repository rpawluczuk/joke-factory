package springapp.jokefactory.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.author.dto.AuthorCreatorDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPresenterDto;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin("http://localhost:4200")
class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    Iterable<AuthorPresenterDto> getAuthorPresenterList(){
        return authorService.getAuthorPresenterList();
    }

    @GetMapping(value = "/list-items")
    Iterable<AuthorItemDto> getAuthorItemList(){
        return authorService.getAuthorItemList();
    }

    @GetMapping(value = "/{id}")
    AuthorCreatorDto getAuthorCreator(@PathVariable("id") Long id){
        return authorService.getAuthorCreator(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addAuthor(@RequestBody AuthorCreatorDto authorCreatorDto){
        authorService.addAuthor(authorCreatorDto);
    }

    @PutMapping
    void editAuthor(@RequestBody AuthorCreatorDto authorCreatorDto){
        authorService.editAuthor(authorCreatorDto);
    }

    @DeleteMapping(value = "/{id}")
    void deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthor(id);
    }
}
