package springapp.jokefactory.categorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;

@RestController
@RequestMapping("/api/categorizations")
@CrossOrigin("http://localhost:4200")
class CategorizationController {

    @Autowired
    CategorizationService categorizationService;

    @GetMapping(value = "/presenter-list")
    Iterable<CategorizationPresenterDto> getCategorizationPresenterList() {
        return categorizationService.getCategorizationPresenterList();
    }

    @PostMapping
    void addCategorization(@RequestBody CategorizationCreatorDto categorizationCreatorDto) {
        categorizationService.addCategorization(categorizationCreatorDto);
    }

    @DeleteMapping(value = "/{id}")
    void deleteCategorization(@PathVariable("id") Long id) {
        categorizationService.deleteCategorization(id);
    }
}
