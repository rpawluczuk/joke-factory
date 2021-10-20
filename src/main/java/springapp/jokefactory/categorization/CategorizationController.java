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

    @GetMapping(value = "/item-list")
    Iterable<CategorizationItemDto> getCategorizationItemList() {
        return categorizationService.getCategorizationItemList();
    }

    @GetMapping(value = "/item-list/{jokeId}")
    Iterable<CategorizationItemDto> getSelectedCategorizationItemList(@PathVariable("jokeId") Long jokeId) {
        return categorizationService.getSelectedCategorizationItemList(jokeId);
    }

    @PostMapping
    void addCategorization(@RequestBody CategorizationCreatorDto categorizationCreatorDto) {
        categorizationService.addCategorization(categorizationCreatorDto);
    }

    @PutMapping
    void editCategorization(@RequestBody CategorizationCreatorDto categorizationCreatorDto) {
        categorizationService.editCategorization(categorizationCreatorDto);
    }

    @DeleteMapping(value = "/{id}")
    void deleteCategorization(@PathVariable("id") Long id) {
        categorizationService.deleteCategorization(id);
    }
}
