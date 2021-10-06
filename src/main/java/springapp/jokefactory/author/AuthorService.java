package springapp.jokefactory.author;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.author.dto.AuthorCreatorDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPresenterDto;
import springapp.jokefactory.joke.JokeFacade;

import java.util.stream.Collectors;

@Service
class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JokeFacade jokeFacade;

    @Autowired
    private AuthorMapper authorMapper;

    Iterable<AuthorPresenterDto> getAuthorPresenterList(){
        return authorRepository.findAll().stream()
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
}
