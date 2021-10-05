package springapp.jokefactory.structure;

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
import springapp.jokefactory.structure.dto.StructureCreatorDto;
import springapp.jokefactory.structure.dto.StructureItemDto;
import springapp.jokefactory.structure.dto.StructurePresenterDto;

@RestController
@RequestMapping("/api/structures")
@CrossOrigin("http://localhost:4200")
class StructureController {

    @Autowired
    StructureService structureService;

    @GetMapping
    Iterable<StructurePresenterDto> getStructurePresenterList(){
        return structureService.getStructurePresenterList();
    }

    @GetMapping(value = "/list-items")
    Iterable<StructureItemDto> getStructureItemList() {
        return structureService.getStructureItemList();
    }

    @GetMapping(value = "/{id}")
    StructureCreatorDto getStructureCreatorDto(@PathVariable("id") Long id){
        return structureService.getStructureCreatorDto(id);
    }

    @GetMapping(value = "by-joke-id/{joke_id}")
    Iterable<StructureItemDto> getStructuresByJokeID(@PathVariable("joke_id") Long jokeID){
        return structureService.getStructureItemListByJokeID(jokeID);
    }

    @PostMapping(consumes={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    void addStructure(@RequestBody StructureCreatorDto structureCreatorDto){
        structureService.addStructure(structureCreatorDto);
    }

    @PutMapping
    void editStructure(@RequestBody StructureCreatorDto structureCreatorDto){
        structureService.editStructure(structureCreatorDto);
    }

    @DeleteMapping(value = "/{id}")
    void deleteStructure(@PathVariable("id") Long id){
        structureService.deleteStructure(id);
    }
}
