package springapp.jokefactory.deserializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import springapp.jokefactory.entity.StructureBlock;
import springapp.jokefactory.repository.StructureRepository;

public class BlockDeserializer extends StdDeserializer<StructureBlock> {

    @Autowired
    StructureRepository blockRepository;

    public BlockDeserializer() {
        this(null);
    }

    public BlockDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StructureBlock deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        StructureBlock structureBlock = new StructureBlock();
        structureBlock.setPosition(node.get("position").asInt());
        structureBlock.setTitle(node.get("title").asText());
        structureBlock.setDescription(node.get("description").asText());
        structureBlock.setBlockType(node.get("blockType").asText());
        if(!node.get("description").isEmpty()){
            structureBlock.setDescription(node.get("description").asText());
        }
        return structureBlock;
    }
}
