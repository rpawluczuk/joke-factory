package springapp.jokefactory.joke;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import springapp.jokefactory.joke.jokeblock.JokeBlockDto;
import springapp.jokefactory.joke.jokeblock.JokeBlock;
import springapp.jokefactory.joke.jokeblock.JokeBlocksAndStructureDto;
import springapp.jokefactory.origin.OriginRepository;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structure.StructureRepository;
import springapp.jokefactory.structure.structureblock.StructureBlockRepository;
import springapp.jokefactory.utils.Pagination;

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
    Pagination pagination;

    @GetMapping()
    public Iterable<Joke> getAllJokes(){
        PageRequest pageRequest = PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Joke> pageJokes = jokeRepository.findAll(pageRequest);
        pagination.setTotalPages(pageJokes.getTotalPages());
        pagination.setTotalItems(pageJokes.getTotalElements());
        return pageJokes.getContent();
    }

    @GetMapping(params = "query")
    public Iterable<Joke> getFilteredJokes(@QuerydslPredicate(root = Joke.class) Predicate predicate){
        PageRequest pageRequest = PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Joke>  pageJokes = jokeRepository.findAll(predicate, pageRequest);
        pagination.setTotalPages(pageJokes.getTotalPages());
        pagination.setTotalItems(pageJokes.getTotalElements());
        return pageJokes.getContent();
    }

    @GetMapping(value = "/{id}")
    public Optional<Joke> getJokeById(@PathVariable("id") Long id){
        return jokeRepository.findById(id);
    }

    @GetMapping(value = "/last")
    public Optional<Joke> getLastJoke(){
        long id = jokeRepository.findHighestID();
        return jokeRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addJoke(@RequestBody JokeCreatorDTO jokeCreatorDTO){
        Joke joke = new Joke(jokeCreatorDTO);
        Set<Structure> structures= jokeCreatorDTO.getJokeBlocksWithStructureDtoList().stream()
                .map(JokeBlocksAndStructureDto::getStructureName)
                .distinct()
                .map(structureName ->  structureRepository.findFirstByName(structureName))
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
        originRepository.findOriginByName(jokeCreatorDTO.getOrigin())
                .ifPresent(joke::setOrigin);
        originRepository.findOriginByName(jokeCreatorDTO.getComedyOrigin())
                .ifPresent(joke::setComedyOrigin);
        originRepository.findOriginByName(jokeCreatorDTO.getOstensibleOrigin())
                .ifPresent(joke::setOstensibleOrigin);
        jokeRepository.save(joke);
    }

    @PutMapping
    public void editJoke(@RequestBody JokeCreatorDTO jokeCreatorDTO){
        Joke joke = new Joke(jokeCreatorDTO);
        Set<Structure> structures = jokeCreatorDTO.getJokeBlocksWithStructureDtoList().stream()
                .map(JokeBlocksAndStructureDto::getStructureName)
                .distinct()
                .map(structureName ->  structureRepository.findFirstByName(structureName))
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
        originRepository.findOriginByName(jokeCreatorDTO.getOrigin())
                .ifPresent(joke::setOrigin);
        originRepository.findOriginByName(jokeCreatorDTO.getComedyOrigin())
                .ifPresent(joke::setComedyOrigin);
        originRepository.findOriginByName(jokeCreatorDTO.getOstensibleOrigin())
                .ifPresent(joke::setOstensibleOrigin);
        jokeRepository.save(joke);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteJoke(@PathVariable("id") Long id){
        Joke jokeToDelete = jokeRepository.findById(id).get();
        jokeRepository.delete(jokeToDelete);
    }
}
