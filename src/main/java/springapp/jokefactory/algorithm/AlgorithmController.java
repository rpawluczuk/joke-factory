package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.algorithm.dto.AlgorithmPresenterDto;
import springapp.jokefactory.algorithm.diagram.DiagramBlockPresenterDto;

@RestController
@RequestMapping("/api/algorithms")
@CrossOrigin("http://localhost:3000")
class AlgorithmController {

    @Autowired
    AlgorithmService algorithmService;

    @GetMapping
    Iterable<AlgorithmPresenterDto> getAlgorithmPresenterList(){
        return algorithmService.getAlgorithmPresenterList();
    }

    @GetMapping(value = "/diagram/{algorithm_id}")
    Iterable<DiagramBlockPresenterDto> getAlgorithmDiagram(@PathVariable("algorithm_id") Long algorithmId){
        return algorithmService.getAlgorithmDiagram(algorithmId);
    }
//
//    @GetMapping(value = "/by-name", params = "name")
//    Iterable<StructurePresenterDto> getStructurePresenterListByName(@RequestParam("name") String name) {
//        return structureService.getStructurePresenterListByName(name);
//    }
//
//    @GetMapping(value = "/list-items")
//    Iterable<StructureItemDto> getStructureItemList() {
//        return structureService.getStructureItemList();
//    }
//
//    @GetMapping(value = "/{id}")
//    StructureCreatorDto getStructureCreatorDto(@PathVariable("id") Long id){
//        return structureService.getStructureCreatorDto(id);
//    }
//
//    @GetMapping(value = "by-joke-id/{joke_id}")
//    Iterable<StructureItemDto> getStructuresByJokeID(@PathVariable("joke_id") Long jokeID){
//        return structureService.getStructureItemListByJokeID(jokeID);
//    }
//
//    @GetMapping(value = "/pagination")
//    StructurePagination getTopicPagination(){
//        return structureService.getStructurePagination();
//    }
//
//    @PostMapping(consumes={"application/json"})
//    @ResponseStatus(HttpStatus.CREATED)
//    void addStructure(@RequestBody StructureCreatorDto structureCreatorDto){
//        structureService.addStructure(structureCreatorDto);
//    }
//
//    @PutMapping
//    void editStructure(@RequestBody StructureCreatorDto structureCreatorDto){
//        structureService.editStructure(structureCreatorDto);
//    }
//
//    @PutMapping(value = "/pagination")
//    void updateStructurePagination(@RequestBody StructurePagination structurePagination){
//        structureService.updateStructurePagination(structurePagination);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    void deleteStructure(@PathVariable("id") Long id){
//        structureService.deleteStructure(id);
//    }
}
