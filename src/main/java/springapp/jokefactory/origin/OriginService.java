package springapp.jokefactory.origin;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import springapp.jokefactory.joke.JokeRepository;
import springapp.jokefactory.origin.dto.OriginCreatorChildDto;
import springapp.jokefactory.origin.dto.OriginCreatorDto;
import springapp.jokefactory.origin.dto.OriginItemDto;
import springapp.jokefactory.origin.dto.OriginPresenterDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class OriginService {

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private OriginRelationRepository originRelationRepository;

    private final OriginMapper originMapper = Mappers.getMapper(OriginMapper.class);

    OriginCreatorDto getOriginCreator(Long id) {
        Origin origin = originRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No origin found with id: " + id));
        Set<Origin> connectedOriginSet = originRepository.findAllConnectedOrigins(origin);
        return originMapper.mapOriginToOriginCreatorDto(origin, connectedOriginSet);
    }

    Iterable<OriginPresenterDto> getOriginPresenterList() {
        List<Origin> originList = originRepository.findAll();
        return originList.stream().map(origin -> {
            Set<Origin> connectedOriginList = originRepository.findAllConnectedOrigins(origin);
            return originMapper.mapOriginToOriginPresenterDto(origin, connectedOriginList);
        }).collect(Collectors.toList());
    }

    Iterable<OriginItemDto> getOriginItemList() {
        return originRepository.findAll().stream()
                .map(originMapper::mapOriginToOriginItemDto)
                .collect(Collectors.toList());
    }

    Iterable<OriginCreatorChildDto> getOriginCreatorChildList(Long parentId) {
        Origin origin = originRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("No origin found with id: " + parentId));
        return originRepository.findAllConnectedOrigins(origin)
                .stream().map(connectedOrigin -> originMapper.mapOriginToOriginCreatorChildDto(connectedOrigin, parentId))
                .collect(Collectors.toList());
    }

    void addOrigin(OriginCreatorDto originCreatorDTO) {
        Origin origin = originRepository.findById(originCreatorDTO.getId())
                .orElseGet(() -> originRepository.save(originMapper.mapOriginCreatorDtoToOrigin(originCreatorDTO)));
        originCreatorDTO.getChildren().forEach(originCreatorDTOChild -> {
            Origin originChild = originRepository.findById(originCreatorDTOChild.getId())
                    .orElseGet(() -> originRepository.save(originMapper.mapOriginCreatorChildDtoToOrigin(originCreatorDTOChild)));
            originRelationRepository.save(new OriginRelation(origin, originChild));
        });
    }

    void addOriginChild(OriginCreatorChildDto originCreatorChildDto) {
        Origin originParent = originRepository.findById(originCreatorChildDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("No origin found with id: " + originCreatorChildDto.getParentId()));
        Origin originChild = originRepository.findById(originCreatorChildDto.getId())
                .orElseGet(() -> originRepository.save(originMapper.mapOriginCreatorChildDtoToOrigin(originCreatorChildDto)));
        originRelationRepository.save(new OriginRelation(originParent, originChild));
    }

    void editOriginName(OriginCreatorDto originCreatorDTO) {
        Origin originToEdit = originRepository.findById(originCreatorDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No origin found with id: " + originCreatorDTO.getId()));
        originToEdit.setName(originCreatorDTO.getName());
        originRepository.save(originToEdit);
    }

    void deleteOrigin(Long id) {
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

    void deleteOriginRelation(Long originParentId, Long originChildId) {
        OriginRelation originRelation = originRelationRepository
                .findById(new OriginRelationKey(originParentId, originChildId))
                .orElseThrow(() -> new IllegalArgumentException("No origin relation found with parent id: "
                        + originParentId + " and child id: " + originChildId));
        originRelationRepository.delete(originRelation);
    }
}
