//package springapp.jokefactory.joke;
//
//import org.junit.Test;
//import org.mapstruct.factory.Mappers;
//import springapp.jokefactory.joke.dto.JokePresenterDto;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class JokeMapperIntegrationTest {
//
//    private final JokeMapper jokeMapper = Mappers.getMapper(JokeMapper.class);
//    private final JokeFactory jokeFactory = new JokeFactory();
//
//    @Test
//    public void givenJokeToJokePresenterDto_whenMaps_thenCorrect() {
//
//        // given
//        Joke joke = jokeFactory.createJoke();
//
//        // when
//        JokePresenterDto jokePresenterDto = jokeMapper.mapJokeToJokePresenterDto(joke);
//
//        // then
//        assertEquals(joke.getId(), jokePresenterDto.getId());
//        assertEquals(joke.getTitle(), jokePresenterDto.getTitle());
//        assertEquals(joke.getContent(), jokePresenterDto.getContent());
//        assertEquals(joke.getAuthor().getName() + " " + joke.getAuthor().getSurname(), jokePresenterDto.getAuthor());
//        assertEquals(joke.getConnectingTopic().getName() , jokePresenterDto.getConnectingTopic());
//        assertEquals(joke.getComedyTopic().getName() , jokePresenterDto.getComedyTopic());
//        assertEquals(joke.getOstensibleTopic().getName() , jokePresenterDto.getOstensibleTopic());
//    }
//}
