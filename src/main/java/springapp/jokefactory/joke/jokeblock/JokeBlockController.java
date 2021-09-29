package springapp.jokefactory.joke.jokeblock;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.StructureMapper;
import springapp.jokefactory.structure.StructureRepository;
import springapp.jokefactory.structure.structureblock.StructureBlock;
import springapp.jokefactory.structure.structureblock.StructureBlockRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/joke-blocks")
@CrossOrigin("http://localhost:4200")
public class JokeBlockController {

    @Autowired
    JokeBlockRepository jokeBlockRepository;

    @Autowired
    StructureBlockRepository structureBlockRepository;

    @Autowired
    StructureRepository structureRepository;

    private static final JokeBlockMapper jokeBlockMapper = Mappers.getMapper(JokeBlockMapper.class);
    private static final StructureMapper structureMapper = Mappers.getMapper(StructureMapper.class);

    @GetMapping
    public Iterable<JokeBlock> getJokeBlocks() {
        return jokeBlockRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<JokeBlock> getJokeBlockById(@PathVariable("id") Long id) {
        return jokeBlockRepository.findById(id);
    }

    @GetMapping(value = "with-joke/{joke_id}")
    public Iterable<JokeBlockDto> getBlocksOfTheJoke(@PathVariable("joke_id") Long jokeID) {
        List<JokeBlock> jokeBlocks = jokeBlockRepository.findBlocksByJoke(jokeID);
        List<StructureBlock> structureBlocks = structureBlockRepository.findStructureBlocksByJoke(jokeID);
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
                .map(jokeBlockMapper::jokeBlockToJokeBlockDto)
                .collect(Collectors.toList());
    }

    @GetMapping(params = "structureId")
    public List<JokeBlockDto> getJokeBlocksOfStructure(@RequestParam("structureId") Long structureId) {
        List<StructureBlock> structureBlocks = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
        return structureBlocks.stream()
                .map(jokeBlockMapper::structureBlockToJokeBlockDto)
                .sorted(Comparator.comparingInt(JokeBlockDto::getPosition))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addJokeBlock(@RequestBody JokeBlock jokeBlock) {
        jokeBlockRepository.save(jokeBlock);
    }

    @PutMapping
    public void editJokeBlock(@RequestBody JokeBlock jokeBlock) {
        jokeBlockRepository.save(jokeBlock);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteJokeBlock(@PathVariable("id") Long id) {
        JokeBlock jokeBlockToDelete = jokeBlockRepository.findById(id).get();
        jokeBlockRepository.delete(jokeBlockToDelete);
    }
}
