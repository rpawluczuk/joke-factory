package springapp.jokefactory.joke;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import springapp.jokefactory.author.AuthorRepository;
import springapp.jokefactory.joke.jokeblock.JokeBlock;
import springapp.jokefactory.joke.jokeblock.JokeBlocksAndStructureDto;
import springapp.jokefactory.origin.OriginRepository;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.StructureRepository;
import springapp.jokefactory.structure.structureblock.StructureBlockRepository;

@RestController
@RequestMapping("/api/jokes")
@CrossOrigin("http://localhost:4200")
public class JokeController {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    StructureBlockRepository structureBlockRepository;

    @Autowired
    OriginRepository originRepository;

    @Autowired
    JokeService jokeService;

    private final JokeMapper jokeMapper = Mappers.getMapper(JokeMapper.class);

    @GetMapping()
    Iterable<JokePresenterDto> getJokePresenterList(){
        return jokeService.getJokePresenterList();
    }

    @GetMapping(params = "query")
    Iterable<JokePresenterDto> getFilteredJokes(@QuerydslPredicate(root = Joke.class) Predicate predicate){
        return jokeService.getFilteredJokePresenterList(predicate);
    }

    @GetMapping(value = "/{id}")
    JokeCreatorDto getJokeById(@PathVariable("id") Long id){
        return jokeService.getJokeCreatorById(id);
    }

//    @GetMapping(value = "/last")
//    public Optional<Joke> getLastJoke(){
//        long id = jokeRepository.findHighestID();
//        return jokeRepository.findById(id);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addJoke(@RequestBody JokeCreatorDto jokeCreatorDTO){
        Joke joke = jokeMapper.mapJokeCreatorDtoToJoke(jokeCreatorDTO);
        Set<Structure> structures= jokeCreatorDTO.getJokeBlocksWithStructureDtoList().stream()
                .map(JokeBlocksAndStructureDto::getStructureItemDto)
                .distinct()
                .map(structureItemDto ->  structureRepository.findFirstByName(structureItemDto.getText()))
                .collect(Collectors.toSet());
        List<JokeBlock> jokeBlocks = jokeCreatorDTO.getJokeBlocksWithStructureDtoList().stream()
                .map(JokeBlocksAndStructureDto::getJokeBlocksDto)
                .flatMap(Collection::stream)
                .map(jokeBlockDto -> {
            return JokeBlock.builder()
                    .structureBlock(structureBlockRepository.findById(jokeBlockDto.getStructureBlockId()).get())
                    .joke(joke)
                    .jokeSnippet(jokeBlockDto.getJokeSnippet())
                    .build();
        }).collect(Collectors.toList());
        joke.setStructures(structures);
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

    @PutMapping
    public void editJoke(@RequestBody JokeCreatorDto jokeCreatorDTO){
        Joke joke = jokeMapper.mapJokeCreatorDtoToJoke(jokeCreatorDTO);
        Set<Structure> structures = jokeCreatorDTO.getJokeBlocksWithStructureDtoList().stream()
                .map(JokeBlocksAndStructureDto::getStructureItemDto)
                .distinct()
                .map(structureItemDto ->  structureRepository.findFirstByName(structureItemDto.getText()))
                .collect(Collectors.toSet());

        List<JokeBlock> jokeBlocks = jokeCreatorDTO.getJokeBlocksWithStructureDtoList().stream()
                .map(JokeBlocksAndStructureDto::getJokeBlocksDto)
                .flatMap(Collection::stream)
                .map(jokeBlockDto -> {
            return JokeBlock.builder()
                    .id(jokeBlockDto.getId())
                    .structureBlock(structureBlockRepository.findById(jokeBlockDto.getStructureBlockId()).get())
                    .joke(joke)
                    .jokeSnippet(jokeBlockDto.getJokeSnippet())
                    .build();
        }).collect(Collectors.toList());
        joke.setStructures(structures);
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

    @DeleteMapping(value = "/{id}")
    public void deleteJoke(@PathVariable("id") Long id){
        Joke jokeToDelete = jokeRepository.findById(id).get();
        jokeRepository.delete(jokeToDelete);
    }
}
