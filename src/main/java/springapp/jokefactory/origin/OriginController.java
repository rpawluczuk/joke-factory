package springapp.jokefactory.origin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import springapp.jokefactory.joke.Joke;
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

    @GetMapping
    public Iterable<Origin> getOrigins(){
        return originRepository.findAll();
    }

    @GetMapping(value = "/get-connected-origins", params = "origin-name")
    public Iterable<Origin> getConnectedOrigins(@RequestParam("origin-name") String originName){
        Origin origin = originRepository.findOriginByName(originName).get();
        Set<Origin> connectedOrigins =  new HashSet<>();
        origin.getChildren().forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginChild());
        });
        origin.getParents().forEach(originRelation -> {
            connectedOrigins.add(originRelation.getOriginChild());
        });
        return connectedOrigins;
    }

    @GetMapping(value = "/{id}")
    public Optional<Origin> getOriginById(@PathVariable("id") Long id){
        return originRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrigin(@RequestBody OriginCreatorDTO originCreatorDTO){
        Origin origin = originRepository.findOriginByName(originCreatorDTO.getName())
                .orElseGet(() -> originRepository.save(new Origin(originCreatorDTO)));
        originCreatorDTO.getChildren().forEach(originCreatorDTOChild -> {
            Origin originChild = originRepository.findOriginByName(originCreatorDTOChild.getName())
                    .orElseGet(() -> originRepository.save(new Origin(originCreatorDTOChild)));
            originRelationRepository.save(new OriginRelation(origin, originChild));
        });
        originCreatorDTO.getParents().forEach(originCreatorDTOParent -> {
            Origin originParent = originRepository.findOriginByName(originCreatorDTOParent.getName())
                    .orElseGet(() -> originRepository.save(new Origin(originCreatorDTOParent)));
            originRelationRepository.save(new OriginRelation(originParent, origin));
        });
    }

    @PutMapping
    public void editOrigin(@RequestBody OriginCreatorDTO originCreatorDTO){
        Origin originToEdit = originRepository.findById(originCreatorDTO.getId()).get();
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
