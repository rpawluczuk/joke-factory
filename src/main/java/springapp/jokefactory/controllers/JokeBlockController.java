package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.dto.JokeBlocksAndStructureDto;
import springapp.jokefactory.entity.JokeBlock;
import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.entity.StructureBlock;
import springapp.jokefactory.repository.JokeBlockRepository;
import springapp.jokefactory.repository.StructureBlockRepository;
import springapp.jokefactory.repository.StructureRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return JokeBlocksAndStructureDto.create(structure, structureBlocks);
    }

    @GetMapping(params = "jokeId")
    public List<JokeBlocksAndStructureDto> getExistingJokeBlocksAndStructure(@RequestParam("jokeId") Long jokeId) {
        List<JokeBlock> jokeBlocks = jokeBlockRepository.findBlocksByJoke(jokeId);

        return JokeBlocksAndStructureDto.create(jokeBlocks);
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
