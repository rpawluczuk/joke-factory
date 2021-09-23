package springapp.jokefactory.joke;

import org.mapstruct.*;
import springapp.jokefactory.author.Author;

@Mapper
public interface JokeMapper {

    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "comedyOrigin", ignore = true)
    @Mapping(target = "ostensibleOrigin", ignore = true)
    Joke mapJokeCreatorDtoToJoke(JokeCreatorDTO jokeCreatorDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "connectingOrigin", source = "joke.origin.name")
    @Mapping(target = "comedyOrigin", source = "joke.comedyOrigin.name")
    @Mapping(target = "ostensibleOrigin", source = "joke.ostensibleOrigin.name")
    @Mapping(target = "author", source = "joke.author", qualifiedByName = "extractAuthor")
    JokePresenterDto mapJokeToJokePresenterDto(Joke joke);

    @Named("extractAuthor")
    default String extractAuthor(Author author) {
        return author.getName() + " " + author.getSurname();
    }
}
