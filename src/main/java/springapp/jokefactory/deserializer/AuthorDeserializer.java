package springapp.jokefactory.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.entity.Author;
import springapp.jokefactory.repository.AuthorRepository;

import java.io.IOException;
import java.util.Optional;

public class AuthorDeserializer extends StdDeserializer<Author> {


    @Autowired
    AuthorRepository authorRepository;

    public AuthorDeserializer() {
        this(null);
    }

    public AuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Author deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Author author = new Author();
        try {
            long id = node.get("id").asLong();
            Optional<Author> optionalEditedAuthor = authorRepository.findById(id);
            if (optionalEditedAuthor.isPresent()){
                author = optionalEditedAuthor.get();
                author.setName(node.get("name").asText());
                author.setSurname(node.get("surname").asText());
                author.setDescription(node.get("description").asText());
                return author;
            }
        } catch (NullPointerException npe){
            author.setName(node.get("name").asText());
            author.setSurname(node.get("surname").asText());
            author.setDescription(node.get("description").asText());
        }
        return author;
    }
}
