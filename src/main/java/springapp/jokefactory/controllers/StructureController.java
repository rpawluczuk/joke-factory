package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/structures")
@CrossOrigin("http://localhost:4200")
public class StructureController {

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    JokeRepository jokeRepository;

    @GetMapping
    public Iterable<Structure> getStructures(){
        return structureRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Structure> getStructureById(@PathVariable("id") Long id){
        return structureRepository.findById(id);
    }

    @PostMapping(consumes={"application/json"})
    public void addStructure(@RequestBody Structure structure){
        structureRepository.save(structure);
    }

    @PutMapping
    public void editStructure(@RequestBody Structure structure){
        structureRepository.save(structure);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteStructure(@PathVariable("id") Long id){
        Structure structureToDelete = structureRepository.findById(id).get();
        structureRepository.delete(structureToDelete);
    }
}
