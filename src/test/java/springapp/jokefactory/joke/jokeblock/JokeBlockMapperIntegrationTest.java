//package springapp.jokefactory.joke.jokeblock;
//
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//import org.springframework.beans.factory.annotation.Autowired;
//import springapp.jokefactory.jokeblock.JokeBlock;
//import springapp.jokefactory.algorithm.jokediagram.JokeBlockFacade;
//import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
//import springapp.jokefactory.jokeblock.JokeBlockFactory;
//import springapp.jokefactory.jokeblock.JokeBlockMapper;
//import springapp.jokefactory.algorithm.StructureBlock;
//import springapp.jokefactory.structureblock.StructureBlockFactory;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//public class JokeBlockMapperIntegrationTest {
//
//    @Autowired
//    JokeBlockFacade jokeBlockFacade;
//
//    private final JokeBlockMapper jokeBlockMapper = Mappers.getMapper(JokeBlockMapper.class);
//    private final StructureBlockFactory structureBlockFactory = new StructureBlockFactory();
//    private final JokeBlockFactory jokeBlockFactory = new JokeBlockFactory();
//
//    @Test
//    public void givenJokeBlockToJokeBlockDTO_whenMaps_thenCorrect() {
//
//        // given
//        JokeBlock jokeBlock = jokeBlockFactory.createJokeBlock(1);
//
//        // when
//        JokeBlockCreatorDto jokeBlockCreatorDto = jokeBlockMapper.jokeBlockToJokeBlockCreatorDto(jokeBlock);
//
//        // then
//        assertEquals(jokeBlock.getId(), jokeBlockCreatorDto.getId());
//        assertEquals(jokeBlock.getJokeSnippet(), jokeBlockCreatorDto.getJokeSnippet());
//        assertEquals(jokeBlock.getStructureBlock().getTitle(), jokeBlockCreatorDto.getStructureBlockPresenterDto().getTitle());
//        assertEquals(jokeBlock.getStructureBlock().getDescription(), jokeBlockCreatorDto.getStructureBlockPresenterDto().getDescription());
//        assertEquals(jokeBlock.getStructureBlock().getPosition(), jokeBlockCreatorDto.getPosition());
//        assertEquals(jokeBlock.getStructureBlock().getId(), jokeBlockCreatorDto.getStructureBlockPresenterDto().getId());
//        assertEquals(jokeBlock.getStructureBlock().getStructure().getId(), jokeBlockCreatorDto.getStructureId());
//    }
//
//    @Test
//    public void givenStructureBlockToJokeBlockDTO_whenMaps_thenCorrect() {
//
//        // given
//        StructureBlock structureBlock = structureBlockFactory.createStructureBlock(1, 1);
//
//        // when
//        JokeBlockCreatorDto jokeBlockCreatorDto = jokeBlockMapper.structureBlockToJokeBlockDto(structureBlock);
//
//        // then
//        assertNull(jokeBlockCreatorDto.getJokeSnippet());
//        assertEquals(structureBlock.getTitle(), jokeBlockCreatorDto.getStructureBlockPresenterDto().getTitle());
//        assertEquals(structureBlock.getDescription(), jokeBlockCreatorDto.getStructureBlockPresenterDto().getDescription());
//        assertEquals(structureBlock.getPosition(), jokeBlockCreatorDto.getPosition());
//        assertEquals(structureBlock.getId(), jokeBlockCreatorDto.getStructureBlockPresenterDto().getId());
//        assertEquals(structureBlock.getStructure().getId(), jokeBlockCreatorDto.getStructureId());
//    }
//}
