package springapp.jokefactory.author;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import springapp.jokefactory.author.dto.AuthorCreatorDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.author.dto.AuthorPresenterDto;

@Mapper
public interface AuthorMapper {

    AuthorPresenterDto mapAuthorToAuthorPresenterDto(Author author);

    AuthorCreatorDto mapAuthorToAuthorCreatorDto(Author author);

    @Mapping(target = "text", source = "author", qualifiedByName = "extractAuthorItemText")
    AuthorItemDto mapAuthorToAuthorItemDto(Author author);

    @Named("extractAuthorItemText")
    default String extractAuthorItemText(Author author) {
        return author.getName() + " " + author.getSurname();
    }

    void updateAuthorFromAuthorCreatorDto(AuthorCreatorDto authorCreatorDto, @MappingTarget Author author);
}
