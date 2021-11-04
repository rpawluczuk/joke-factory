package springapp.jokefactory.categorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationItemDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;

@RestController
@RequestMapping("/api/categorizations")
@CrossOrigin("http://localhost:4200")
class CategorizationController {

    @Autowired
    CategorizationService categorizationService;

    @GetMapping(value = "/{id}")
    CategorizationCreatorDto getCategorizationCreator(@PathVariable("id") Long id) {
        return categorizationService.getCategorizationCreator(id);
    }

    @GetMapping(value = "/presenter-list")
    Iterable<CategorizationPresenterDto> getCategorizationPresenterList() {
        return categorizationService.getCategorizationPresenterList();
    }

    @GetMapping(value = "/presenter-list/by-name", params = "name")
    Iterable<CategorizationPresenterDto> getTopicPresenterListByName(@RequestParam("name") String name) {
        return categorizationService.getCategorizationPresenterListByName(name);
    }

    @GetMapping(value = "/item-list")
    Iterable<CategorizationItemDto> getCategorizationItemList() {
        return categorizationService.getCategorizationItemList();
    }

    @GetMapping(value = "/item-list/{jokeId}")
    Iterable<CategorizationItemDto> getSelectedCategorizationItemList(@PathVariable("jokeId") Long jokeId) {
        return categorizationService.getSelectedCategorizationItemList(jokeId);
    }

    @GetMapping(value = "/pagination")
    CategorizationPagination getCategorizationPagination(){
        return categorizationService.getCategorizationPagination();
    }


    @PostMapping
    void addCategorization(@RequestBody CategorizationCreatorDto categorizationCreatorDto) {
        categorizationService.addCategorization(categorizationCreatorDto);
    }

    @PutMapping
    void editCategorization(@RequestBody CategorizationCreatorDto categorizationCreatorDto) {
        categorizationService.editCategorization(categorizationCreatorDto);
    }

    @PutMapping(value = "/pagination")
    void updateCategorizationPagination(@RequestBody CategorizationPagination categorizationPagination){
        categorizationService.updateCategorizationPagination(categorizationPagination);
    }

    @DeleteMapping(value = "/{id}")
    void deleteCategorization(@PathVariable("id") Long id) {
        categorizationService.deleteCategorization(id);
    }
}
