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

    @Mapping(target = "label", source = "author", qualifiedByName = "extractAuthorItemLabel")
    @Mapping(target = "value", source = "id")
    abstract AuthorItemDto mapAuthorToAuthorItemDto(Author author);

    @Named("extractAuthorItemLabel")
    String extractAuthorItemLabel(Author author) {
        return author.getName() + " " + author.getSurname();
    }

    abstract void updateAuthorFromAuthorCreatorDto(AuthorCreatorDto authorCreatorDto, @MappingTarget Author author);
}
