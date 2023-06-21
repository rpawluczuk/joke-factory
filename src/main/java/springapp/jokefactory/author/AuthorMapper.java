package springapp.jokefactory.author;

import org.springframework.stereotype.Service;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorDto;

@Service
class AuthorMapper {

    AuthorDto mapAuthorToDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .description(author.getDescription())
                .dateCreated(author.getDateCreated())
                .build();
    }

    AuthorItemDto mapAuthorToAuthorItemDto(Author author) {
        return new AuthorItemDto(author.getName() + " " + author.getSurname(), author.getId());
    }

    public Author updateAuthor(AuthorDto authorDto, Author author) {
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        author.setDescription(authorDto.getDescription());
        return author;
    }
}
