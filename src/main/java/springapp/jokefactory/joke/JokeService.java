package springapp.jokefactory.joke;

import com.querydsl.core.types.Predicate;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.jokeblock.JokeBlock;
import springapp.jokefactory.joke.jokeblock.JokeBlockMapper;
import springapp.jokefactory.origin.OriginRepository;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.StructureRepository;
import springapp.jokefactory.structure.structureblock.StructureBlock;
import springapp.jokefactory.structure.structureblock.StructureBlockRepository;
import springapp.jokefactory.utils.Pagination;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class JokeService {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    StructureBlockRepository structureBlockRepository;

    @Autowired
    OriginRepository originRepository;

    @Autowired
    Pagination pagination;

    private final JokeMapper jokeMapper = Mappers.getMapper(JokeMapper.class);
    private final JokeBlockMapper jokeBlockMapper = Mappers.getMapper(JokeBlockMapper.class);

    Iterable<JokePresenterDto> getJokePresenterList(){
        PageRequest pageRequest = PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Joke> pageJokes = jokeRepository.findAll(pageRequest);
        pagination.setTotalPages(pageJokes.getTotalPages());
        pagination.setTotalItems(pageJokes.getTotalElements());
        return pageJokes.getContent().stream()
                .map(jokeMapper::mapJokeToJokePresenterDto)
                .collect(Collectors.toList());
    }

    Iterable<JokePresenterDto> getFilteredJokePresenterList(Predicate predicate){
        PageRequest pageRequest = PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Joke>  pageJokes = jokeRepository.findAll(predicate, pageRequest);
        pagination.setTotalPages(pageJokes.getTotalPages());
        pagination.setTotalItems(pageJokes.getTotalElements());
        return pageJokes.getContent().stream()
                .map(jokeMapper::mapJokeToJokePresenterDto)
                .collect(Collectors.toList());
    }

    JokeCreatorDto getJokeCreatorById(Long id){
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No joke found with id: " + id));
        return jokeMapper.mapJokeToJokeCreatorDto(joke);
    }

    void addJoke(JokeCreatorDto jokeCreatorDTO) {
        Joke joke = jokeMapper.mapJokeCreatorDtoToJoke(jokeCreatorDTO);
        Set<Structure> structures = jokeCreatorDTO.getStructureItemList().stream()
                .map(structureItemDto -> structureRepository.findById(structureItemDto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + structureItemDto.getId())))
                .collect(Collectors.toSet());
        joke.setStructures(structures);
        List<JokeBlock> jokeBlocks = jokeCreatorDTO.getJokeBlockDtoList().stream()
                .map(jokeBlockDto -> {
                    StructureBlock structureBlock = structureBlockRepository.findById(jokeBlockDto.getStructureBlockId())
                            .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + jokeBlockDto.getStructureBlockId()));
                    return jokeBlockMapper.jokeBlockDtoToJokeBlock(jokeBlockDto, joke, structureBlock);
                }).collect(Collectors.toList());
        joke.setJokeBlocks(jokeBlocks);
        if (jokeCreatorDTO.getOrigin() != null) {
            originRepository.findOriginByName(jokeCreatorDTO.getOrigin().getName())
                    .ifPresent(joke::setOrigin);
        }
        if (jokeCreatorDTO.getComedyOrigin() != null) {
            originRepository.findOriginByName(jokeCreatorDTO.getComedyOrigin().getName())
                    .ifPresent(joke::setComedyOrigin);
        }
        if (jokeCreatorDTO.getOstensibleOrigin() != null) {
            originRepository.findOriginByName(jokeCreatorDTO.getOstensibleOrigin().getName())
                    .ifPresent(joke::setOstensibleOrigin);
        }
        jokeRepository.save(joke);
    }

    void editJoke(JokeCreatorDto jokeCreatorDTO) {
        Joke joke = jokeMapper.mapJokeCreatorDtoToJoke(jokeCreatorDTO);
        Set<Structure> structures = jokeCreatorDTO.getStructureItemList().stream()
                .map(structureItemDto -> structureRepository.findById(structureItemDto.getId())
                        .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + structureItemDto.getId())))
                .collect(Collectors.toSet());
        joke.setStructures(structures);
        List<JokeBlock> jokeBlocks = jokeCreatorDTO.getJokeBlockDtoList().stream()
                .map(jokeBlockDto -> {
                    StructureBlock structureBlock = structureBlockRepository.findById(jokeBlockDto.getStructureBlockId())
                            .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + jokeBlockDto.getStructureBlockId()));
                    return jokeBlockMapper.jokeBlockDtoToJokeBlock(jokeBlockDto, joke, structureBlock);
                }).collect(Collectors.toList());
        joke.setJokeBlocks(jokeBlocks);
        if (jokeCreatorDTO.getOrigin() != null) {
            originRepository.findOriginByName(jokeCreatorDTO.getOrigin().getName())
                    .ifPresent(joke::setOrigin);
        }
        if (jokeCreatorDTO.getComedyOrigin() != null) {
            originRepository.findOriginByName(jokeCreatorDTO.getComedyOrigin().getName())
                    .ifPresent(joke::setComedyOrigin);
        }
        if (jokeCreatorDTO.getOstensibleOrigin() != null) {
            originRepository.findOriginByName(jokeCreatorDTO.getOstensibleOrigin().getName())
                    .ifPresent(joke::setOstensibleOrigin);
        }
        jokeRepository.save(joke);
    }

    void deleteJoke(Long id) {
        Joke jokeToDelete = jokeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No joke found with id: " + id));
        jokeRepository.delete(jokeToDelete);
    }
}
