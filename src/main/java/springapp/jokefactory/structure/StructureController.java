package springapp.jokefactory.structure;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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

import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.origin.dto.OriginItemDto;
import springapp.jokefactory.structure.structureblock.StructureBlockRepository;
import springapp.jokefactory.joke.JokeRepository;

@RestController
@RequestMapping("/api/structures")
@CrossOrigin("http://localhost:4200")
public class StructureController {

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureBlockRepository structureBlockRepository;

    private final StructureMapper structureMapper = Mappers.getMapper(StructureMapper.class);

    @GetMapping
    public Iterable<Structure> getStructures(){
        return structureRepository.findAll();
    }

    @GetMapping(value = "/list-items")
    Iterable<StructureItemDto> getStructureItemList() {
        return structureRepository.findAll().stream()
                .map(structureMapper::mapStructureToStructureItemDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public Optional<Structure> getStructureById(@PathVariable("id") Long id){
        return structureRepository.findById(id);
    }

    @GetMapping(value = "/last")
    public Optional<Structure> getLastStructure(){
        long id = structureRepository.findHighestID();
        return structureRepository.findById(id);
    }

    @GetMapping(value = "by-joke-id/{joke_id}")
    public Iterable<StructureItemDto> getStructuresByJokeID(@PathVariable("joke_id") Long jokeID){
        return structureRepository.findStructuresByJokeID(jokeID).stream()
                .map(structureMapper::mapStructureToStructureItemDto)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
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
        Set<Joke> jokes = structureToDelete.getJokes();
        for (Joke joke : jokes) {
            joke.getStructures().remove(structureToDelete);
            jokeRepository.save(joke);
        }
        structureRepository.delete(structureToDelete);
    }
}
