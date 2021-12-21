package springapp.jokefactory.joke;

import com.google.common.collect.Sets;
import springapp.jokefactory.author.AuthorFactory;
import springapp.jokefactory.jokeblock.JokeBlockFactory;
import springapp.jokefactory.topic.TopicFactory;
import springapp.jokefactory.structure.StructureFactory;

public class JokeFactory {

    private final StructureFactory structureFactory = new StructureFactory();
    private final AuthorFactory authorFactory = new AuthorFactory();
    private final TopicFactory topicFactory = new TopicFactory();
    private final JokeBlockFactory jokeBlockFactory = new JokeBlockFactory();

    public Joke createJoke() {
        Joke joke = Joke.builder()
                .id(1L)
                .title("joke title")
                .content("joke content")
                .structures(structureFactory.createStructureSet(1, 3))
                .author(authorFactory.createAuthor())
                .jokeBlocks(jokeBlockFactory.createJokeBlockSet(1, 5))
                .build();
        joke.getStructures().forEach(structure -> structure.setJokes(Sets.newHashSet(joke)));
        joke.getAuthor().setJokes(Sets.newHashSet(joke));
        return joke;
    }
}
