package springapp.jokefactory.joke;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.author.AuthorFacade;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.categorization.Categorization;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.joke.dto.JokePresenterDto;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.dto.StructureItemDto;
import springapp.jokefactory.topicgroup.TopicGroup;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
abstract class OldJokeMapper {

    @Autowired
    protected AuthorFacade authorFacade;

    @Mapping(target = "author", source = "authorItem", qualifiedByName = "transformAuthorItemToAuthor")
    abstract Joke mapJokeCreatorDtoToJoke(JokeCreatorDto jokeCreatorDTO);

    @Named("transformAuthorItemToAuthor")
    Author transformAuthorItemToAuthor(AuthorItemDto authorItemDto) {
        if (authorItemDto != null) {
            return authorFacade.tryToGetAuthorById(authorItemDto.getValue());
        }
        return null;
    }


    @Named("extractCategorizationList")
    List<String> extractCategorizationList(List<TopicGroup> topicGroups) {
        return topicGroups.stream()
                .map(TopicGroup::getCategorization)
                .map(Categorization::getName)
                .collect(Collectors.toList());
    }

    @Named("extractAuthor")
    String extractAuthor(Author author) {
        return author.getName() + " " + author.getSurname();
    }

    @Named("handleRate")
    Float handleRate(Rate rate) {
        if (rate.getValue() < 3 && rate.getCount() != null && rate.getCount() >= 3) {
            return (float) 7;
        }
        return rate.getValue();
    }

    @Mapping(target = "structureItemList", source = "joke.structures", qualifiedByName = "extractStructureItem")
    @Mapping(target = "authorItem", source = "author", qualifiedByName = "transformAuthorToAuthorItem")
    abstract JokeCreatorDto mapJokeToJokeCreatorDto(Joke joke);

    @Named("transformAuthorToAuthorItem")
    AuthorItemDto transformAuthorToAuthorItem(Author author) {
        return new AuthorItemDto(author.getId(), extractAuthor(author));
    }

    @Named("extractStructureItem")
    List<StructureItemDto> extractStructureItem(Set<Structure> structures) {
        return structures.stream()
                .map(structure -> new StructureItemDto(structure.getId(), structure.getName()))
                .collect(Collectors.toList());
    }

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "author", source = "authorItem", qualifiedByName = "transformAuthorItemToAuthor")
    abstract void updateJokeFromJokeCreatorDto(JokeCreatorDto jokeCreatorDto, @MappingTarget Joke joke);
}
