package springapp.jokefactory.jokeblock;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
import springapp.jokefactory.jokeblock.dto.JokeBlockPresenterDto;
import springapp.jokefactory.structureblock.StructureBlock;
import springapp.jokefactory.structureblock.StructureBlockFacade;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class JokeBlockService {

    @Autowired
    private JokeBlockRepository jokeBlockRepository;

    @Autowired
    private StructureBlockFacade structureBlockFacade;

    @Autowired
    private JokeBlockMapper jokeBlockMapper;

    Iterable<JokeBlockCreatorDto> getJokeBlockCreatorList(Long jokeId) {
        List<JokeBlock> jokeBlocks = jokeBlockRepository.findBlocksByJoke(jokeId);
        List<StructureBlock> structureBlocks = structureBlockFacade.getStructureBlocksByJoke(jokeId);
        structureBlocks.forEach(structureBlock -> {
            Optional<JokeBlock> result = jokeBlocks.stream().filter(jokeBlock ->
                    jokeBlock.getStructureBlock().getId().equals(structureBlock.getId())).findAny();
            if (!result.isPresent()) {
                JokeBlock newJokeBlock = new JokeBlock();
                newJokeBlock.setStructureBlock(structureBlock);
                jokeBlocks.add(newJokeBlock);
            }
        });
        return jokeBlocks.stream()
                .map(jokeBlockMapper::jokeBlockToJokeBlockCreatorDto)
                .collect(Collectors.toList());
    }

    Iterable<JokeBlockPresenterDto> getJokeBlockPresenterList(Long jokeId) {
        List<JokeBlock> jokeBlocks = jokeBlockRepository.findBlocksByJoke(jokeId);
        return jokeBlocks.stream()
                .map(jokeBlockMapper::jokeBlockToJokeBlockPresenterDto)
                .collect(Collectors.toList());
    }

    List<JokeBlockCreatorDto> getJokeBlockCreatorListOfStructure(Long structureId) {
        List<StructureBlock> structureBlocks = structureBlockFacade.getStructureBlocksByStructure(structureId);
        return structureBlocks.stream()
                .map(jokeBlockMapper::structureBlockToJokeBlockDto)
                .sorted(Comparator.comparingInt(JokeBlockCreatorDto::getPosition))
                .collect(Collectors.toList());
    }
}
