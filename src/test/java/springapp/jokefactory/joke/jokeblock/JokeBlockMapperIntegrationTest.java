package springapp.jokefactory.joke.jokeblock;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.structureblock.StructureBlock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JokeBlockMapperIntegrationTest {

    private final JokeBlockMapper jokeBlockMapper = Mappers.getMapper(JokeBlockMapper.class);

    @Test
    public void givenJokeBlockToJokeBlockDTO_whenMaps_thenCorrect() {
        Structure structure = Structure.builder()
                .name("structure test name")
                .build();

        StructureBlock structureBlock = StructureBlock.builder()
                .title("test title")
                .description("test description")
                .position(2)
                .id(11L)
                .structure(structure)
                .build();

        JokeBlock jokeBlock = JokeBlock.builder()
                .id(1L)
                .jokeSnippet("test joke snippet")
                .structureBlock(structureBlock)
                .build();
        JokeBlockDto jokeBlockDto = jokeBlockMapper.jokeBlockToJokeBlockDto(jokeBlock);

        assertEquals(jokeBlock.getId(), jokeBlockDto.getId());
        assertEquals(jokeBlock.getJokeSnippet(), jokeBlockDto.getJokeSnippet());
        assertEquals(jokeBlock.getStructureBlock().getTitle(), jokeBlockDto.getTitle());
        assertEquals(jokeBlock.getStructureBlock().getDescription(), jokeBlockDto.getDescription());
        assertEquals(jokeBlock.getStructureBlock().getPosition(), jokeBlockDto.getPosition());
        assertEquals(jokeBlock.getStructureBlock().getId(), jokeBlockDto.getStructureBlockId());
        assertEquals(jokeBlock.getStructureBlock().getStructure().getName(), jokeBlockDto.getStructureName());
    }

    @Test
    public void givenStructureBlockToJokeBlockDTO_whenMaps_thenCorrect() {
        Structure structure = Structure.builder()
                .name("structure test name")
                .build();

        StructureBlock structureBlock = StructureBlock.builder()
                .title("test title")
                .description("test description")
                .position(2)
                .id(11L)
                .structure(structure)
                .build();

        JokeBlockDto jokeBlockDto = jokeBlockMapper.structureBlockToJokeBlockDto(structureBlock);

        assertNull(jokeBlockDto.getJokeSnippet());
        assertEquals(structureBlock.getTitle(), jokeBlockDto.getTitle());
        assertEquals(structureBlock.getDescription(), jokeBlockDto.getDescription());
        assertEquals(structureBlock.getPosition(), jokeBlockDto.getPosition());
        assertEquals(structureBlock.getId(), jokeBlockDto.getStructureBlockId());
        assertEquals(structureBlock.getStructure().getName(), jokeBlockDto.getStructureName());
    }
}
