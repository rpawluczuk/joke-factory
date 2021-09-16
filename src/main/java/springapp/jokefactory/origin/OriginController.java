package springapp.jokefactory.origin;

import java.util.*;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.joke.JokeMapper;
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

    private final OriginMapper originMapper = Mappers.getMapper(OriginMapper.class);

    @GetMapping
    public Iterable<Origin> getOrigins(){
        return originRepository.findAll();
    }

    @GetMapping(value = "/get-connected-origins", params = "origin-name")
    public Iterable<Origin> getConnectedOrigins(@RequestParam("origin-name") String originName){
        Origin origin = originRepository.findOriginByName(originName).get();
        List<OriginRelation> originRelationListForChild = originRelationRepository.findOriginRelationsByOriginChild(origin).get();
        Set<Origin> connectedOrigins = new HashSet<>();
        origin.getChildren().forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginChild());
        });
        originRelationListForChild.forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginParent());
        });
        return connectedOrigins;
    }

    @GetMapping(value = "/{id}")
    public Optional<Origin> getOriginById(@PathVariable("id") Long id){
        return originRepository.findById(id);
    }

    @GetMapping(params = "originCreatorName")
    public Optional<OriginCreatorDto> getOriginCreatorDtoByName(@RequestParam("originCreatorName") String originCreatorName){
        Origin origin = originRepository.findOriginByName(originCreatorName).get();
        List<OriginRelation> originRelationListForChild = originRelationRepository.findOriginRelationsByOriginChild(origin).get();
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
    public void addOrigin(@RequestBody OriginCreatorDto originCreatorDTO){
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
    public void editOrigin(@RequestBody OriginCreatorDto originCreatorDTO){
        Origin originToEdit = originRepository.findOriginByName(originCreatorDTO.getName()).get();
        originToEdit.setName(originCreatorDTO.getName());
        originRepository.save(originToEdit);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrigin(@PathVariable("id") Long id){
        Origin originToDelete = originRepository.findById(id).get();
        Set<Joke> jokes = originToDelete.getJokes();
        for (Joke joke: jokes) {
            joke.setOrigin(null);
            jokeRepository.save(joke);
        }
        originRepository.delete(originToDelete);
    }

    @DeleteMapping(value = "/remove-relation", params = {"origin-parent-id", "origin-child-id"})
    public void deleteOriginRelation(@RequestParam("origin-parent-id") Long originParentId,
                                     @RequestParam("origin-child-id")Long originChildId ){
        OriginRelation originRelation = originRelationRepository
                .findById(new OriginRelationKey(originParentId, originChildId)).get();
        originRelationRepository.delete(originRelation);
    }
}
