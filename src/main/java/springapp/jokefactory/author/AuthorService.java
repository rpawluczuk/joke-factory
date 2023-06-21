package springapp.jokefactory.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPaginationDto;
import springapp.jokefactory.author.dto.AuthorDto;
import springapp.jokefactory.joke.JokeFacade;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JokeFacade jokeFacade;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorPaginationDto authorPaginationDto;

    Iterable<AuthorDto> getAuthorDtoList() {
        PageRequest pageRequest = PageRequest.of(authorPaginationDto.getCurrentPage(), authorPaginationDto.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Author> authorPage = authorRepository.findAll(pageRequest);
        authorPaginationDto.setTotalPages(authorPage.getTotalPages());
        authorPaginationDto.setTotalItems(authorPage.getTotalElements());
        return authorPage.getContent().stream()
                .map(authorMapper::mapAuthorToDto)
                .collect(Collectors.toList());
    }

    Iterable<AuthorItemDto> getAuthorItemList() {
        AuthorItemDto defaultItem = new AuthorItemDto("All", null);
        return Stream.concat(
                Stream.of(defaultItem),
                authorRepository.findAll().stream()
                        .map(authorMapper::mapAuthorToAuthorItemDto)
        ).collect(Collectors.toList());
    }

    AuthorDto getAuthorDto(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No author found with id: " + id));
        return authorMapper.mapAuthorToDto(author);
    }

    AuthorPaginationDto getAuthorPagination() {
        return authorPaginationDto;
    }

    void addAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author = authorMapper.updateAuthor(authorDto, author);
        authorRepository.save(author);
    }

    void editAuthor(AuthorDto authorDto) {
        Author author = authorRepository.findById(authorDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("No author found with id: " + authorDto.getId()));
        authorMapper.updateAuthor(authorDto, author);
        authorRepository.save(author);
    }

    void deleteAuthor(Long id) {
        Author authorToDelete = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No author found with id: " + id));
        jokeFacade.removeAuthorFromJokes(authorToDelete);
        authorRepository.delete(authorToDelete);
    }

    void updateAuthorPagination(AuthorPaginationDto authorPaginationDto) {
        this.authorPaginationDto.setCurrentPage(authorPaginationDto.getCurrentPage());
        this.authorPaginationDto.setPageSize(authorPaginationDto.getPageSize());
        PageRequest pageRequest = PageRequest.of(authorPaginationDto.getCurrentPage(), authorPaginationDto.getPageSize());
        Page<Author> authorPage = authorRepository.findAll(pageRequest);
        this.authorPaginationDto.setTotalPages(authorPage.getTotalPages());
        this.authorPaginationDto.setTotalItems(authorPage.getTotalElements());
    }
}
