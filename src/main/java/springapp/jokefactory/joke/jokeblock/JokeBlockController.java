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
    public JokeBlocksAndStructureDto getJokeBlocksAndStructure(@RequestParam("structureId") Long structureId) {
        List<StructureBlock> structureBlocks = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
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
        HashMap<Structure, List<JokeBlockDto>> jokeBlockMap = new HashMap<>();
        structureList.forEach(structure -> {
            List<JokeBlockDto> filteredJokeBlockDtoList = jokeBlocks.stream()
                    .filter(jokeBlock -> structure.equals(jokeBlock.getStructureBlock().getStructure()))
                    .map(jokeBlockMapper::jokeBlockToJokeBlockDto)
                    .collect(Collectors.toList());
            List<StructureBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structure.getId());
            if (filteredJokeBlockDtoList.isEmpty() && structureBlockList.size() > 0) {
                filteredJokeBlockDtoList = structureBlockList.stream()
                        .map(jokeBlockMapper::structureBlockToJokeBlockDto)
                        .collect(Collectors.toList());
            }
            jokeBlockMap.put(structure, filteredJokeBlockDtoList);
        });
        return jokeBlockMapper.hashMapToJokeBlocksAndStructureDto(jokeBlockMap);
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
