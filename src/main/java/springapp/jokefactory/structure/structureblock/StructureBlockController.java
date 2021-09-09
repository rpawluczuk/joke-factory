package springapp.jokefactory.structure.structureblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.structure.StructureRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/blocks")
@CrossOrigin("http://localhost:4200")
public class StructureBlockController {

    @Autowired
    StructureBlockRepository structureBlockRepository;

    @Autowired
    StructureRepository structureRepository;

    @GetMapping
    public Iterable<StructureBlock> getStructureBlocks(){
        return structureBlockRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<StructureBlock> getStructureBlockById(@PathVariable("id") Long id){
        return structureBlockRepository.findById(id);
    }

    @GetMapping(value = "with-structure/{structure_id}")
    public Iterable<StructureBlock> getBlocksOfTheStructure(@PathVariable("structure_id") Long structureID){
        return structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addStructureBlock(@RequestBody StructureBlock structureBlock){
        structureBlockRepository.save(structureBlock);
    }

    @PutMapping
    public void editStructureBlock(@RequestBody StructureBlock structureBlock){
        structureBlockRepository.save(structureBlock);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteStructureBlock(@PathVariable("id") Long id){
        StructureBlock structureBlockToDelete = structureBlockRepository.findById(id).get();
        structureBlockRepository.delete(structureBlockToDelete);
    }
}
