package springapp.jokefactory.deserializer;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import springapp.jokefactory.entity.Origin;
import springapp.jokefactory.repository.OriginRepository;

public class OriginDeserializer extends StdDeserializer<Origin> {


    @Autowired
    OriginRepository originRepository;

    public OriginDeserializer() {
        this(null);
    }

    public OriginDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Origin deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Origin origin = new Origin();
        try {
            long id = node.get("id").asLong();
            Optional<Origin> optionalEditedOrigin = originRepository.findById(id);
            if (optionalEditedOrigin.isPresent()){
                origin = optionalEditedOrigin.get();
                origin.setName(node.get("name").asText());
                return origin;
            }
        } catch (NullPointerException npe){
            origin.setName(node.get("name").asText());
        }
        return origin;
    }
}
