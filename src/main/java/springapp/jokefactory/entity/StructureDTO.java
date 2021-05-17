package springapp.jokefactory.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import springapp.jokefactory.deserializer.StructureDTODeserializer;

@JsonDeserialize(using = StructureDTODeserializer.class)
public class StructureDTO {

    private Long id;
    private String name;
    private String description;
    private JsonNode blockScheme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getBlockScheme() {
        return blockScheme;
    }

    public void setBlockScheme(JsonNode blockScheme) {
        this.blockScheme = blockScheme;
    }
}
