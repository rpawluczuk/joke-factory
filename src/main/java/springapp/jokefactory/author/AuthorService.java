package springapp.jokefactory.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.author.dto.AuthorCreatorDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPaginationDto;
import springapp.jokefactory.author.dto.AuthorPresenterDto;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.dto.TopicPaginationDto;

import java.util.stream.Collectors;

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

    Iterable<AuthorPresenterDto> getAuthorPresenterList(){
        PageRequest pageRequest = PageRequest.of(authorPaginationDto.getCurrentPage(), authorPaginationDto.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Author> authorPage = authorRepository.findAll(pageRequest);
        authorPaginationDto.setTotalPages(authorPage.getTotalPages());
        authorPaginationDto.setTotalItems(authorPage.getTotalElements());
        return authorPage.getContent().stream()
                .map(authorMapper::mapAuthorToAuthorPresenterDto)
                .collect(Collectors.toList());
    }

    Iterable<AuthorItemDto> getAuthorItemList() {
        return authorRepository.findAll().stream()
                .map(authorMapper::mapAuthorToAuthorItemDto)
                .collect(Collectors.toList());
    }

    AuthorCreatorDto getAuthorCreator(Long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No author found with id: " + id));
        return authorMapper.mapAuthorToAuthorCreatorDto(author);
    }

    AuthorPaginationDto getAuthorPagination() {
        return authorPaginationDto;
    }

    void addAuthor(AuthorCreatorDto authorCreatorDto){
        Author author = new Author();
        authorMapper.updateAuthorFromAuthorCreatorDto(authorCreatorDto, author);
        authorRepository.save(author);
    }

    void editAuthor(AuthorCreatorDto authorCreatorDto){
        Author author = authorRepository.findById(authorCreatorDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("No author found with id: " + authorCreatorDto.getId()));
        authorMapper.updateAuthorFromAuthorCreatorDto(authorCreatorDto, author);
        authorRepository.save(author);
    }

    void deleteAuthor(Long id){
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
