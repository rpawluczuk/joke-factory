package springapp.jokefactory.author;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import springapp.jokefactory.author.dto.AuthorCreatorDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPresenterDto;

@Mapper(componentModel = "spring")
abstract class AuthorMapper {

    abstract AuthorPresenterDto mapAuthorToAuthorPresenterDto(Author author);

    abstract AuthorCreatorDto mapAuthorToAuthorCreatorDto(Author author);

    @Mapping(target = "text", source = "author", qualifiedByName = "extractAuthorItemText")
    abstract AuthorItemDto mapAuthorToAuthorItemDto(Author author);

    @Named("extractAuthorItemText")
    String extractAuthorItemText(Author author) {
        return author.getName() + " " + author.getSurname();
    }

    abstract void updateAuthorFromAuthorCreatorDto(AuthorCreatorDto authorCreatorDto, @MappingTarget Author author);
}
