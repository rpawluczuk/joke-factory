package springapp.jokefactory.deserializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import springapp.jokefactory.entity.Block;
import springapp.jokefactory.repository.StructureRepository;

public class BlockDeserializer extends StdDeserializer<Block> {

    @Autowired
    StructureRepository blockRepository;

    public BlockDeserializer() {
        this(null);
    }

    public BlockDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Block deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Block block = new Block();
        block.setPosition(node.get("position").asInt());
        block.setTitle(node.get("title").asText());
        block.setBlockType(node.get("blockType").asText());
        if(!node.get("description").isEmpty()){
            block.setDescription(node.get("description").asText());
        }

        return block;
    }
}
