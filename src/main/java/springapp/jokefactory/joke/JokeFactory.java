package springapp.jokefactory.joke;

import com.google.common.collect.Sets;
import springapp.jokefactory.author.AuthorFactory;
import springapp.jokefactory.topic.TopicFactory;
import springapp.jokefactory.algorithm.StructureFactory;

public class JokeFactory {

    private final StructureFactory structureFactory = new StructureFactory();
    private final AuthorFactory authorFactory = new AuthorFactory();
    private final TopicFactory topicFactory = new TopicFactory();
//    private final JokeBlockFactory jokeBlockFactory = new JokeBlockFactory();

    public Joke createJoke() {
        Joke joke = Joke.builder()
                .id(1L)
                .title("joke title")
                .content("joke content")
                .algorithms(structureFactory.createStructureSet(1, 3))
                .author(authorFactory.createAuthor())
//                .jokeBlocks(jokeBlockFactory.createJokeBlockSet(1, 5))
                .build();
        joke.getAlgorithms().forEach(structure -> structure.setJokes(Sets.newHashSet(joke)));
        joke.getAuthor().setJokes(Sets.newHashSet(joke));
        return joke;
    }
}
