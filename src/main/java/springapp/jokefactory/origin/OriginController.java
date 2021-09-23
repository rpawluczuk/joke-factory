package springapp.jokefactory.origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.joke.JokeRepository;
import springapp.jokefactory.origin.dto.OriginCreatorChildDto;
import springapp.jokefactory.origin.dto.OriginCreatorDto;
import springapp.jokefactory.origin.dto.OriginItemDto;
import springapp.jokefactory.origin.dto.OriginPresenterDto;

@RestController
@RequestMapping("/api/origins")
@CrossOrigin("http://localhost:4200")
class OriginController {

    @Autowired
    private OriginService originService;

    @GetMapping
    Iterable<OriginPresenterDto> getOriginPresenterList() {
        return originService.getOriginPresenterList();
    }

    @GetMapping(value = "/origin-creator-children", params = "parent-id")
    Iterable<OriginCreatorChildDto> getOriginCreatorChildList(@RequestParam("parent-id") Long parentId) {
        return originService.getOriginCreatorChildList(parentId);
    }

    @GetMapping(value = "/list-items")
    Iterable<OriginItemDto> getOriginItemList() {
        return originService.getOriginItemList();
    }

    @GetMapping(value = "/{id}")
    OriginCreatorDto getOriginCreator(@PathVariable("id") Long id) {
        return originService.getOriginCreator(id);
    }

    @PostMapping
    void addOrigin(@RequestBody OriginCreatorDto originCreatorDTO) {
        originService.addOrigin(originCreatorDTO);
    }

    @PostMapping(value = "/add-origin-child")
    void addOriginChild(@RequestBody OriginCreatorChildDto originCreatorChildDto) {
        originService.addOriginChild(originCreatorChildDto);
    }

    @PatchMapping
    void editOriginName(@RequestBody OriginCreatorDto originCreatorDTO) {
        originService.editOriginName(originCreatorDTO);
    }

    @DeleteMapping(value = "/{id}")
    void deleteOrigin(@PathVariable("id") Long id) {
        originService.deleteOrigin(id);
    }

    @DeleteMapping(value = "/remove-relation", params = {"origin-parent-id", "origin-child-id"})
    void deleteOriginRelation(@RequestParam("origin-parent-id") Long originParentId,
                              @RequestParam("origin-child-id") Long originChildId) {
        originService.deleteOriginRelation(originParentId, originChildId);
    }
}
