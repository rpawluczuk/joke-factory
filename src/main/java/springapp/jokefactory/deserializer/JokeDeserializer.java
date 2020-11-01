package springapp.jokefactory.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.repository.AuthorRepository;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

import java.io.IOException;
import java.util.*;

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
        if (node.get("id") == null){
            joke.setTitle(node.get("title").asText());
            joke.setContent(node.get("content").asText());
            Iterator<JsonNode> structuresInJsonNodeFormat = node.get("structures").elements();
            Set<Structure> selectedStructures = new HashSet<>();
            structuresInJsonNodeFormat.forEachRemaining(s -> selectedStructures.add(structureRepository.findById(s.get("id").asLong()).get()));
            joke.setStructures(selectedStructures);
            if (node.get("author").get("id") != null){
                long authorID = node.get("author").get("id").asLong();
                joke.setAuthor(authorRepository.findById(authorID).get());
            } else {
                joke.setAuthor(null);
            }
        }

//        long id = node.get("id").asLong();

//        Optional<Joke> optionalEditedJoke = jokeRepository.findById(id);
//        if (optionalEditedJoke.isPresent()) {
//            joke = optionalEditedJoke.get();
//            joke.setTitle(node.get("title").asText());
//            return joke;
//        }
        return joke;
    }
}
