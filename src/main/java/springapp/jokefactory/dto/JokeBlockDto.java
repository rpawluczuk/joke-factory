package springapp.jokefactory.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.entity.StructureBlock;

@Data
@Builder
public class JokeBlockDto {

    private String jokeSnippet;
    private String structureName;
    private String title;
    private String description;
    private int position;
    private long structureBlockId;

    static JokeBlockDto create(StructureBlock structureBlock, String structureName) {
        return JokeBlockDto.builder()
                .jokeSnippet(null)
                .structureName(structureName)
                .title(structureBlock.getTitle())
                .description(structureBlock.getDescription())
                .position(structureBlock.getPosition())
                .structureBlockId(structureBlock.getId())
                .build();
    }
}
