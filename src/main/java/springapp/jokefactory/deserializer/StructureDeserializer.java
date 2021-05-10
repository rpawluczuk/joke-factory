package springapp.jokefactory.deserializer;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.repository.StructureRepository;

public class StructureDeserializer extends StdDeserializer<Structure> {

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    StructureRepository blockRepository;

    public StructureDeserializer() {
        this(null);
    }

    public StructureDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Structure deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Structure structure = new Structure();
        try {
            long id = node.get("id").asLong();
            Optional<Structure> optionalEditedStructure = structureRepository.findById(id);
            if (optionalEditedStructure.isPresent()){
                structure = optionalEditedStructure.get();
                structure.setName(node.get("name").asText());
                structure.setDescription(node.get("description").asText());
                return structure;
            }
        } catch (NullPointerException npe){
            structure.setName(node.get("name").asText());
            structure.setDescription(node.get("description").asText());
        }
        return structure;
    }
}
