package springapp.jokefactory.joke;

import org.mapstruct.*;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.joke.dto.JokePresenterDto;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.dto.StructureItemDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface JokeMapper {

    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "comedyOrigin", ignore = true)
    @Mapping(target = "ostensibleOrigin", ignore = true)
    Joke mapJokeCreatorDtoToJoke(JokeCreatorDto jokeCreatorDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "connectingOrigin", source = "joke.origin.name")
    @Mapping(target = "comedyOrigin", source = "joke.comedyOrigin.name")
    @Mapping(target = "ostensibleOrigin", source = "joke.ostensibleOrigin.name")
    @Mapping(target = "author", source = "joke.author", qualifiedByName = "extractAuthor")
    JokePresenterDto mapJokeToJokePresenterDto(Joke joke);

    @Mapping(target = "origin", source = "joke.origin")
    @Mapping(target = "comedyOrigin", source = "joke.comedyOrigin")
    @Mapping(target = "ostensibleOrigin", source = "joke.ostensibleOrigin")
    @Mapping(target = "structureItemList", source = "joke.structures", qualifiedByName = "extractStructureItem")
    JokeCreatorDto mapJokeToJokeCreatorDto(Joke joke);

    @Named("extractAuthor")
    default String extractAuthor(Author author) {
        return author.getName() + " " + author.getSurname();
    }

    @Named("extractStructureItem")
    default List<StructureItemDto> extractStructureItem(Set<Structure> structures) {
        return structures.stream()
                .map(structure -> new StructureItemDto(structure.getId(), structure.getName()))
                .collect(Collectors.toList());
    }
}
