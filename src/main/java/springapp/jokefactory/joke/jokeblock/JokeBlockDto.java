package springapp.jokefactory.joke.jokeblock;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.structure.structureblock.StructureBlock;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class JokeBlockDto {

    private long id;
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

    static List<JokeBlockDto> create(List<JokeBlock> jokeBlocks, String structureName) {
        return jokeBlocks.stream().map(jokeBlock -> {
            return JokeBlockDto.builder()
                    .id(jokeBlock.getId())
                    .jokeSnippet(jokeBlock.getJokeSnippet())
                    .structureName(structureName)
                    .title(jokeBlock.getStructureBlock().getTitle())
                    .description(jokeBlock.getStructureBlock().getDescription())
                    .position(jokeBlock.getStructureBlock().getPosition())
                    .structureBlockId(jokeBlock.getStructureBlock().getId())
                    .build();
        }).collect(Collectors.toList());
    }
}
