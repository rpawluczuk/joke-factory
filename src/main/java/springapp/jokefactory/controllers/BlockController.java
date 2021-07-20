package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.entity.StructureBlock;
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
    public Iterable<StructureBlock> getBlocks(){
        return blockRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<StructureBlock> getBlockById(@PathVariable("id") Long id){
        return blockRepository.findById(id);
    }

    @GetMapping(value = "with-structure/{structure_id}")
    public Iterable<StructureBlock> getBlocksOfTheStructure(@PathVariable("structure_id") Long structureID){
        return blockRepository.findBlocksByStructure(structureID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBlock(@RequestBody StructureBlock structureBlock){
        blockRepository.save(structureBlock);
    }

    @PutMapping
    public void editBlock(@RequestBody StructureBlock structureBlock){
        blockRepository.save(structureBlock);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBlock(@PathVariable("id") Long id){
        StructureBlock structureBlockToDelete = blockRepository.findById(id).get();
        blockRepository.delete(structureBlockToDelete);
    }
}
