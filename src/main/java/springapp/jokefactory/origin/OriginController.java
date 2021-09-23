package springapp.jokefactory.origin;

import java.util.*;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springapp.jokefactory.joke.JokeRepository;

@RestController
@RequestMapping("/api/origins")
@CrossOrigin("http://localhost:4200")
public class OriginController {

    @Autowired
    OriginRepository originRepository;

    @Autowired
    OriginRelationRepository originRelationRepository;

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    OriginService originService;

    private final OriginMapper originMapper = Mappers.getMapper(OriginMapper.class);

    @GetMapping
    Iterable<OriginPresenterDto> getOriginPresenters() {
        return originService.getOriginPresenters();
    }

    @GetMapping(value = "/origin-creator-children", params = "origin-id")
    public Iterable<OriginCreatorChildDto> getOriginCreatorChildList(@RequestParam("origin-id") Long id) {
        Origin origin = originRepository.findById(id).get();
        return getConnectedOrigins(origin).stream()
                .map(connectedOrigin -> originMapper.mapOriginToOriginCreatorChildDto(connectedOrigin, id))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/list-items")
    public Iterable<OriginItemDto> getOriginListItems() {
        return originRepository.findAll().stream()
                .map(originMapper::mapOriginToOriginItemDto)
                .collect(Collectors.toList());
    }

    private List<Origin> getConnectedOrigins(Origin origin) {
        List<OriginRelation> originRelationListForChild = originRelationRepository.findOriginRelationsByOriginChild(origin)
                .orElse(Collections.emptyList());
        List<Origin> connectedOrigins = new ArrayList<>();
        origin.getChildren().forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginChild());
        });
        originRelationListForChild.forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginParent());
        });
        return connectedOrigins;
    }

    @GetMapping(value = "/{id}")
    Optional<Origin> getOriginById(@PathVariable("id") Long id) {
        return originService.getOriginById(id);
    }

    @GetMapping(params = "originCreatorName")
    public Optional<OriginCreatorDto> getOriginCreatorDtoByName(@RequestParam("originCreatorName") String originCreatorName) {
        Origin origin = originRepository.findOriginByName(originCreatorName).get();
        List<OriginRelation> originRelationListForChild = originRelationRepository.findOriginRelationsByOriginChild(origin)
                .orElse(Collections.emptyList());
        List<Origin> connectedOrigins = new ArrayList<>();
        origin.getChildren().forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginChild());
        });
        originRelationListForChild.forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginParent());
        });
        OriginCreatorDto originCreatorDTO = originMapper.mapOriginToOriginCreatorDto(origin, connectedOrigins);
        return Optional.ofNullable(originCreatorDTO);
    }

    @PostMapping
    public void addOrigin(@RequestBody OriginCreatorDto originCreatorDTO) {
        Origin origin = originRepository.findOriginByName(originCreatorDTO.getName())
                .orElseGet(() -> originRepository.save(new Origin(originCreatorDTO.getName())));
        originCreatorDTO.getChildren().forEach(originCreatorDTOChild -> {
            Origin originChild = originRepository.findOriginByName(originCreatorDTOChild.getName())
                    .orElseGet(() -> originRepository.save(new Origin(originCreatorDTOChild.getName())));
            originRelationRepository.save(new OriginRelation(origin, originChild));
        });
    }

    @PostMapping(value = "/add-origin-child")
    public void addOriginChild(@RequestBody OriginCreatorChildDto originCreatorChildDto) {
        Origin originParent = originRepository.findById(originCreatorChildDto.getParentId()).get();
        Origin originChild = originRepository.findOriginByName(originCreatorChildDto.getName())
                .orElseGet(() -> originRepository.save(originMapper.mapOriginCreatorChildDtoToOrigin(originCreatorChildDto)));
        originRelationRepository.save(new OriginRelation(originParent, originChild));
    }

    @PutMapping
    public void editOrigin(@RequestBody OriginCreatorDto originCreatorDTO) {
        Origin originToEdit = originRepository.findOriginByName(originCreatorDTO.getName()).get();
        originToEdit.setName(originCreatorDTO.getName());
        originRepository.save(originToEdit);
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
