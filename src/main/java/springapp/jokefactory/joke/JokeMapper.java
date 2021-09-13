package springapp.jokefactory.joke;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface JokeMapper {

    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "comedyOrigin", ignore = true)
    @Mapping(target = "ostensibleOrigin", ignore = true)
    Joke mapJokeCreatorDtoToJoke(JokeCreatorDTO jokeCreatorDTO);
}
