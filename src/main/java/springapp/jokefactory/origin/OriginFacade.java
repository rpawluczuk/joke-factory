package springapp.jokefactory.origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.JokeRepository;

import java.util.Optional;

@Service
public class OriginFacade {

    @Autowired
    OriginRepository originRepository;

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    OriginRelationRepository originRelationRepository;

    public Optional<Origin> getOriginById(Long id) {
        return originRepository.findById(id);
    }

    public void deleteOrigin(Long id) {
        Origin originToDelete = originRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No origin found with id: " + id));
        originToDelete.getJokes().forEach(joke -> {
            joke.setOrigin(null);
            jokeRepository.save(joke);
        });
        originToDelete.getJokesAsOstensibleContext().forEach(joke -> {
            joke.setOstensibleOrigin(null);
            jokeRepository.save(joke);
        });
        originToDelete.getJokesAsComedyContext().forEach(joke -> {
            joke.setComedyOrigin(null);
            jokeRepository.save(joke);
        });
        originRelationRepository.findAllOriginRelationsConnectedWithOrigin(originToDelete.getId())
                .forEach(originRelationRepository::delete);
        originRepository.delete(originToDelete);
    }

    public void deleteOriginRelation(Long originParentId, Long originChildId) {
        OriginRelation originRelation = originRelationRepository
                .findById(new OriginRelationKey(originParentId, originChildId))
                .orElseThrow(() -> new IllegalArgumentException("No origin relation found with parent id: "
                        + originParentId + " and child id: " + originChildId));
        originRelationRepository.delete(originRelation);
    }
}
