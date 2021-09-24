package springapp.jokefactory.joke;

import com.querydsl.core.types.Predicate;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.utils.Pagination;

import java.util.stream.Collectors;

@Service
public class JokeService {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    Pagination pagination;

    private final JokeMapper jokeMapper = Mappers.getMapper(JokeMapper.class);

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
}
