package springapp.jokefactory.origin;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.JokeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OriginService {

    @Autowired
    OriginRepository originRepository;

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    OriginRelationRepository originRelationRepository;

    private final OriginMapper originMapper = Mappers.getMapper(OriginMapper.class);

    public Optional<Origin> getOriginById(Long id) {
        return originRepository.findById(id);
    }

    public Iterable<OriginPresenterDto> getOriginPresenters() {
        List<Origin> originList = originRepository.findAll();
        return originList.stream().map(origin -> {
            Set<Origin> connectedOriginList = originRepository.findAllConnectedOrigins(origin);
            return originMapper.mapOriginToOriginPresenterDto(origin, connectedOriginList);
        }).collect(Collectors.toList());
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
