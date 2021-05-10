package springapp.jokefactory.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import springapp.jokefactory.entity.StructureDTO;

public class StructureDTODeserializer extends StdDeserializer<StructureDTO> {

    public StructureDTODeserializer() {
        this(null);
    }

    public StructureDTODeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StructureDTO deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        StructureDTO structureDTO = new StructureDTO();
        structureDTO.setName(node.get("name").asText());
        structureDTO.setDescription(node.get("description").asText());
        structureDTO.setBlockScheme(node.get("blockScheme"));
        return structureDTO;
    }
}
