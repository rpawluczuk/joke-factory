package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.algorithm.dto.AlgorithmDto;
import springapp.jokefactory.algorithm.algorithmblock.dto.AlgorithmBlockDto;
import springapp.jokefactory.algorithm.dto.AlgorithmItemDto;

@RestController
@RequestMapping("/api/algorithms")
@CrossOrigin("http://localhost:3000")
class AlgorithmController {

    @Autowired
    AlgorithmService algorithmService;

    @GetMapping
    Iterable<AlgorithmDto> getAlgorithmPresenterList(){
        return algorithmService.getAlgorithmPresenterList();
    }

    @GetMapping(value = "/diagram/{algorithm_id}")
    Iterable<AlgorithmBlockDto> getAlgorithmBlockList(@PathVariable("algorithm_id") Long algorithmId){
        return algorithmService.getAlgorithmBlockList(algorithmId);
    }

//
//    @GetMapping(value = "/by-name", params = "name")
//    Iterable<StructurePresenterDto> getStructurePresenterListByName(@RequestParam("name") String name) {
//        return structureService.getStructurePresenterListByName(name);
//    }

    @GetMapping(value = "/item-list")
    Iterable<AlgorithmItemDto> getAlgorithmItemList(){
        return algorithmService.getAlgorithmItemList();
    }

    @GetMapping(value = "/{id}")
    AlgorithmDto getAlgorithmDto(@PathVariable("id") Long id){
        return algorithmService.getAlgorithmDto(id);
    }

    @GetMapping(value = "/pagination")
    AlgorithmPagination getAlgorithmPagination(){
        return algorithmService.getAlgorithmPagination();
    }

    @GetMapping(value = "/item-list/{joke_id}")
    Iterable<AlgorithmItemDto> handleDetails(@PathVariable("joke_id") Long jokeID){
        return algorithmService.getAlgorithmItemListByJokeID(jokeID);
    }

    @PostMapping(consumes={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    void addAlgorithm(@RequestBody AlgorithmDto algorithmDto){
        algorithmService.addAlgorithm(algorithmDto);
    }

    @PutMapping
    void editAlgorithm(@RequestBody AlgorithmDto algorithmDto){
        algorithmService.editAlgorithm(algorithmDto);
    }

    @PutMapping(value = "/pagination")
    void updateAlgorithmPagination(@RequestBody AlgorithmPagination algorithmPagination){
        algorithmService.updateAlgorithmPagination(algorithmPagination);
    }

    @DeleteMapping(value = "/{id}")
    void deleteAlgorithm(@PathVariable("id") Long id){
        algorithmService.deleteAlgorithm(id);
    }
}
