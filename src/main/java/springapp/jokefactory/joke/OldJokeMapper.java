package springapp.jokefactory.joke;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.author.AuthorFacade;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.categorization.Categorization;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.algorithm.dto.AlgorithmItemDto;
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

    @Named("transformAuthorToAuthorItem")
    AuthorItemDto transformAuthorToAuthorItem(Author author) {
        return new AuthorItemDto(extractAuthor(author), author.getId());
    }

    @Named("extractStructureItem")
    List<AlgorithmItemDto> extractStructureItem(Set<Algorithm> structures) {
        return structures.stream()
                .map(structure -> new AlgorithmItemDto(structure.getName(), structure.getId()))
                .collect(Collectors.toList());
    }

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "author", source = "authorItem", qualifiedByName = "transformAuthorItemToAuthor")
    abstract void updateJokeFromJokeCreatorDto(JokeCreatorDto jokeCreatorDto, @MappingTarget Joke joke);
}
