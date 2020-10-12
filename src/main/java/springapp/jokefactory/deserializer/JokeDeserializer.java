package springapp.jokefactory.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.repository.AuthorRepository;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

import java.io.IOException;
import java.util.Optional;

public class JokeDeserializer extends StdDeserializer<Joke> {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    AuthorRepository authorRepository;

    public JokeDeserializer() {
        this(null);
    }

    public JokeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Joke deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Joke joke = new Joke();
        try {
            long id = node.get("id").asLong();
            Optional<Joke> optionalEditedJoke = jokeRepository.findById(id);
            if (optionalEditedJoke.isPresent()){
                joke = optionalEditedJoke.get();
                joke.setTitle(node.get("title").asText());
                return joke;
            }
        } catch (NullPointerException npe){
            joke.setTitle(node.get("title").asText());
            joke.setContent(node.get("content").asText());
            long structureID = node.get("structure").get("id").asLong();
            joke.setStructure(structureRepository.findById(structureID).get());
            long authorID = node.get("author").get("id").asLong();
            joke.setAuthor(authorRepository.findById(authorID).get());
        }
        return joke;
    }
}
