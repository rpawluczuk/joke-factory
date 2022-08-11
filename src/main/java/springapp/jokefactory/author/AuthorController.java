package springapp.jokefactory.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.author.dto.AuthorCreatorDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPaginationDto;
import springapp.jokefactory.author.dto.AuthorPresenterDto;
import springapp.jokefactory.topic.dto.TopicPaginationDto;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin("http://localhost:3000")
class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    Iterable<AuthorPresenterDto> getAuthorPresenterList() {
        return authorService.getAuthorPresenterList();
    }

    @GetMapping(value = "/list-items")
    Iterable<AuthorItemDto> getAuthorItemList() {
        return authorService.getAuthorItemList();
    }

    @GetMapping(value = "/creator/{id}")
    AuthorCreatorDto getAuthorCreator(@PathVariable("id") Long id) {
        return authorService.getAuthorCreator(id);
    }

    @GetMapping(value = "/pagination")
    AuthorPaginationDto getAuthorPagination() {
        return authorService.getAuthorPagination();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addAuthor(@RequestBody AuthorCreatorDto authorCreatorDto) {
        authorService.addAuthor(authorCreatorDto);
    }

    @PutMapping
    void editAuthor(@RequestBody AuthorCreatorDto authorCreatorDto) {
        authorService.editAuthor(authorCreatorDto);
    }

    @DeleteMapping(value = "/{id}")
    void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }

    @PutMapping(value = "/pagination")
    void updateAuthorPagination(@RequestBody AuthorPaginationDto authorPaginationDto){
        authorService.updateAuthorPagination(authorPaginationDto);
    }
}
