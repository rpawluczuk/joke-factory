package springapp.jokefactory.joke;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.joke.dto.JokePresenterDto;
import springapp.jokefactory.jokeblock.JokeBlock;
import springapp.jokefactory.jokeblock.JokeBlockFacade;
import springapp.jokefactory.origin.OriginFacade;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.StructureFacade;
import springapp.jokefactory.structureblock.StructureBlockFacade;
import springapp.jokefactory.utils.Pagination;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class JokeService {

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private StructureFacade structureFacade;

    @Autowired
    private JokeBlockFacade jokeBlockFacade;

    @Autowired
    private StructureBlockFacade structureBlockFacade;

    @Autowired
    private OriginFacade originFacade;

    @Autowired
    private Pagination pagination;

    @Autowired
    private JokeMapper jokeMapper;

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

    void addJoke(JokeCreatorDto jokeCreatorDto) {
        Joke joke = jokeMapper.mapJokeCreatorDtoToJoke(jokeCreatorDto);
        Set<Structure> structures = jokeCreatorDto.getStructureItemList().stream()
                .map(structureItemDto -> structureFacade.tryToGetStructureById(structureItemDto.getId()))
                .collect(Collectors.toSet());
        joke.setStructures(structures);
        originFacade.tryToGetOriginByOriginItem(jokeCreatorDto.getOrigin()).ifPresent(joke::setOrigin);
        originFacade.tryToGetOriginByOriginItem(jokeCreatorDto.getComedyOrigin()).ifPresent(joke::setComedyOrigin);
        originFacade.tryToGetOriginByOriginItem(jokeCreatorDto.getOstensibleOrigin()).ifPresent(joke::setOstensibleOrigin);
        jokeRepository.save(joke);
        List<JokeBlock> jokeBlockList = jokeBlockFacade.extractJokeBlockList(jokeCreatorDto.getJokeBlockCreatorDtoList(), joke);
        jokeBlockFacade.saveJokeBlockList(jokeBlockList);
    }

    void editJoke(JokeCreatorDto jokeCreatorDto) {
        Joke joke = jokeMapper.mapJokeCreatorDtoToJoke(jokeCreatorDto);
        Set<Structure> structures = jokeCreatorDto.getStructureItemList().stream()
                .map(structureItemDto -> structureFacade.tryToGetStructureById(structureItemDto.getId()))
                .collect(Collectors.toSet());
        joke.setStructures(structures);
        List<JokeBlock> jokeBlocks = jokeBlockFacade.extractJokeBlockList(jokeCreatorDto.getJokeBlockCreatorDtoList(), joke);
        joke.setJokeBlocks(jokeBlocks);
        originFacade.tryToGetOriginByOriginItem(jokeCreatorDto.getOrigin()).ifPresent(joke::setOrigin);
        originFacade.tryToGetOriginByOriginItem(jokeCreatorDto.getComedyOrigin()).ifPresent(joke::setComedyOrigin);
        originFacade.tryToGetOriginByOriginItem(jokeCreatorDto.getOstensibleOrigin()).ifPresent(joke::setOstensibleOrigin);
        jokeRepository.save(joke);
    }

    void deleteJoke(Long id) {
        Joke jokeToDelete = jokeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No joke found with id: " + id));
        jokeRepository.delete(jokeToDelete);
    }
}
