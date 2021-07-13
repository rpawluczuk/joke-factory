package springapp.jokefactory.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import springapp.jokefactory.entity.Block;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.entity.StructureDTO;
import springapp.jokefactory.repository.BlockRepository;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

@RestController
@RequestMapping("/api/structures")
@CrossOrigin("http://localhost:4200")
public class StructureController {

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    BlockRepository blockRepository;

    @GetMapping
    public Iterable<Structure> getStructures(){
        return structureRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Structure> getStructureById(@PathVariable("id") Long id){
        return structureRepository.findById(id);
    }

    @PostMapping(consumes={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addStructure(@RequestBody StructureDTO structureDTO){
        ObjectMapper mapper = new ObjectMapper();
        Structure structure = mapper.convertValue(structureDTO, Structure.class);
        structure = structureRepository.save(structure);
        JsonNode arrayNode = structureDTO.getBlockScheme();
        if (arrayNode.isArray()){
            for (final JsonNode objNode : arrayNode) {
                Block block = mapper.convertValue(objNode, Block.class);
                block.setStructure(structure);
                blockRepository.save(block);
            }
        }
    }

    @PutMapping
    public void editStructure(@RequestBody StructureDTO structureDTO){
        ObjectMapper mapper = new ObjectMapper();
        Structure structureToUpdate = structureRepository.findById(structureDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Joke not found for this id :: " + structureDTO.getId()));
        List<Block> blocksToDelete = blockRepository.findBlocksByStructure(structureToUpdate);
        for (Block blockToDelete : blocksToDelete) {
            blockRepository.delete(blockToDelete);
        }
        structureToUpdate.setName(structureDTO.getName());
        structureToUpdate.setDescription(structureDTO.getDescription());
        Structure structureUpdated = structureRepository.save(structureToUpdate);
        JsonNode arrayNode = structureDTO.getBlockScheme();
        if (arrayNode.isArray()){
            for (final JsonNode objNode : arrayNode) {
                Block block = mapper.convertValue(objNode, Block.class);
                block.setStructure(structureUpdated);
                blockRepository.save(block);
            }
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteStructure(@PathVariable("id") Long id){
        Structure structureToDelete = structureRepository.findById(id).get();
        Set<Joke> jokes = structureToDelete.getJokes();
        for (Joke joke : jokes) {
            joke.getStructures().remove(structureToDelete);
            jokeRepository.save(joke);
        }
        structureRepository.delete(structureToDelete);
    }
}
