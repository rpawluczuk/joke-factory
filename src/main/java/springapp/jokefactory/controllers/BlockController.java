package springapp.jokefactory.controllers;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.entity.Block;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.repository.BlockRepository;
import springapp.jokefactory.repository.StructureRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/blocks")
@CrossOrigin("http://localhost:4200")
public class BlockController {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    StructureRepository structureRepository;

    @GetMapping
    public Iterable<Block> getBlocks(){
        return blockRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Block> getBlockById(@PathVariable("id") Long id){
        return blockRepository.findById(id);
    }

    @GetMapping(value = "with-structure/{structure_id}")
    public Iterable<Block> getBlocksOfTheStructure(@PathVariable("structure_id") Long structureID){
        return blockRepository.findBlocksByStructure(structureID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBlock(@RequestBody Block block){
        blockRepository.save(block);
    }

    @PutMapping
    public void editBlock(@RequestBody Block block){
        blockRepository.save(block);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBlock(@PathVariable("id") Long id){
        Block blockToDelete = blockRepository.findById(id).get();
        blockRepository.delete(blockToDelete);
    }
}
