package springapp.jokefactory.joke.jokeblock;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.structure.Structure;
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

    @GetMapping
    public Iterable<JokeBlock> getJokeBlocks() {
        return jokeBlockRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<JokeBlock> getJokeBlockById(@PathVariable("id") Long id) {
        return jokeBlockRepository.findById(id);
    }

    @GetMapping(value = "with-joke/{joke_id}")
    public Iterable<JokeBlock> getBlocksOfTheJoke(@PathVariable("joke_id") Long jokeID) {
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
        return jokeBlocks;
    }

    @GetMapping(params = "structureId")
    public JokeBlocksAndStructureDto getJokeBlocksAndStructure(@RequestParam("structureId") Long structureId) {
        Set<StructureBlock> structureBlocks = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
        Structure structure = structureRepository.findById(structureId).get();
        List<JokeBlockDto> jokeBlockDtoList = structureBlocks.stream()
                .map(jokeBlockMapper::structureBlockToJokeBlockDto)
                .sorted(Comparator.comparingInt(JokeBlockDto::getPosition))
                .collect(Collectors.toList());

        return JokeBlocksAndStructureDto.builder()
                .structureName(structure.getName())
                .jokeBlocksDto(jokeBlockDtoList)
                .build();
    }

    @GetMapping(params = "jokeId")
    public List<JokeBlocksAndStructureDto> getExistingJokeBlocksAndStructure(@RequestParam("jokeId") Long jokeId) {
        List<Structure> structureList = structureRepository.findStructuresByJokeID(jokeId);
        List<JokeBlock> jokeBlocks = jokeBlockRepository.findBlocksByJoke(jokeId);
        Map<Structure, List<JokeBlock>> jokeBlockMap = new HashMap<>();
        structureList.forEach(structure -> {
            List<JokeBlock> filteredJokeBlockList = jokeBlocks.stream()
                    .filter(jokeBlock -> structure.equals(jokeBlock.getStructureBlock().getStructure()))
                    .collect(Collectors.toList());
            jokeBlockMap.put(structure, filteredJokeBlockList);
        });
        return jokeBlockMap.entrySet().stream().map(entrySet -> {
            List<JokeBlockDto> jokeBlocksDto = entrySet.getValue().stream()
                    .map(jokeBlockMapper::jokeBlockToJokeBlockDto)
                    .collect(Collectors.toList());

            return JokeBlocksAndStructureDto.builder()
                    .structureName(entrySet.getKey().getName())
                    .jokeBlocksDto(jokeBlocksDto)
                    .build();
        }).collect(Collectors.toList());
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
