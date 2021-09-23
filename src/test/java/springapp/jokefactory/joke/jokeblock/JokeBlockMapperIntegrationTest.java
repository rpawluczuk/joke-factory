package springapp.jokefactory.joke.jokeblock;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import springapp.jokefactory.structure.structureblock.StructureBlock;
import springapp.jokefactory.structure.structureblock.StructureBlockFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JokeBlockMapperIntegrationTest {

    private final JokeBlockMapper jokeBlockMapper = Mappers.getMapper(JokeBlockMapper.class);
    private final StructureBlockFactory structureBlockFactory = new StructureBlockFactory();
    private final JokeBlockFactory jokeBlockFactory = new JokeBlockFactory();

    @Test
    public void givenJokeBlockToJokeBlockDTO_whenMaps_thenCorrect() {

        // given
        JokeBlock jokeBlock = jokeBlockFactory.createJokeBlock(1);

        // when
        JokeBlockDto jokeBlockDto = jokeBlockMapper.jokeBlockToJokeBlockDto(jokeBlock);

        // then
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

        // given
        StructureBlock structureBlock = structureBlockFactory.createStructureBlock(1, 1);

        // when
        JokeBlockDto jokeBlockDto = jokeBlockMapper.structureBlockToJokeBlockDto(structureBlock);

        // then
        assertNull(jokeBlockDto.getJokeSnippet());
        assertEquals(structureBlock.getTitle(), jokeBlockDto.getTitle());
        assertEquals(structureBlock.getDescription(), jokeBlockDto.getDescription());
        assertEquals(structureBlock.getPosition(), jokeBlockDto.getPosition());
        assertEquals(structureBlock.getId(), jokeBlockDto.getStructureBlockId());
        assertEquals(structureBlock.getStructure().getName(), jokeBlockDto.getStructureName());
    }
}
