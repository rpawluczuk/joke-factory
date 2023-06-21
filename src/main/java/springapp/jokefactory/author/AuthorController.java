package springapp.jokefactory.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPaginationDto;
import springapp.jokefactory.author.dto.AuthorDto;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin("http://localhost:3000")
class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    Iterable<AuthorDto> getAuthorDtoList() {
        return authorService.getAuthorDtoList();
    }

    @GetMapping(value = "/list-items")
    Iterable<AuthorItemDto> getAuthorItemList() {
        return authorService.getAuthorItemList();
    }

    @GetMapping(value = "/creator/{id}")
    AuthorDto getAuthorCreator(@PathVariable("id") Long id) {
        return authorService.getAuthorDto(id);
    }

    @GetMapping(value = "/pagination")
    AuthorPaginationDto getAuthorPagination() {
        return authorService.getAuthorPagination();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addAuthor(@RequestBody AuthorDto authorDto) {
        authorService.addAuthor(authorDto);
    }

    @PutMapping
    void editAuthor(@RequestBody AuthorDto authorDto) {
        authorService.editAuthor(authorDto);
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
