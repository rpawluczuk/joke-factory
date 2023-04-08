package springapp.jokefactory.joke;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.joke.dto.JokePresenterDto;
import springapp.jokefactory.joke.dto.JokeRateDto;
import springapp.jokefactory.jokeblock.JokeBlockFacade;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.algorithm.StructureFacade;
//import springapp.jokefactory.topicgroup.TopicGroupFacade;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

//    @Autowired
//    private TopicGroupFacade topicGroupFacade;

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private JokePagination jokePagination;

    @Autowired
    private OldJokeMapper oldJokeMapper;

    @Autowired
    private JokeMapper jokeMapper;

    @Autowired
    private JokeFacade jokeFacade;

    Iterable<JokePresenterDto> getJokePresenterList() {
        PageRequest pageRequest = PageRequest.of(jokePagination.getCurrentPage(), jokePagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Joke> pageJokes = jokeRepository.findAll(pageRequest);
        jokePagination.setTotalPages(pageJokes.getTotalPages());
        jokePagination.setTotalItems(pageJokes.getTotalElements());
        return pageJokes.getContent().stream()
                .map(jokeMapper::mapJokeToJokePresenterDto)
                .collect(Collectors.toList());
    }

    Iterable<JokePresenterDto> getFilteredJokePresenterList(Predicate predicate) {
        PageRequest pageRequest = PageRequest.of(jokePagination.getCurrentPage(), jokePagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Joke> pageJokes = jokeRepository.findAll(predicate, pageRequest);
        jokePagination.setTotalPages(pageJokes.getTotalPages());
        jokePagination.setTotalItems(pageJokes.getTotalElements());
        return pageJokes.getContent().stream()
                .map(jokeMapper::mapJokeToJokePresenterDto)
                .collect(Collectors.toList());
    }

    JokeCreatorDto getJokeCreatorById(Long id) {
        Joke joke = jokeFacade.getJokeById(id);
        JokeCreatorDto jokeCreatorDto = oldJokeMapper.mapJokeToJokeCreatorDto(joke);
//     jokeCreatorDto.setTopicGroupCreatorList(topicGroupFacade.mapTopicGroupListToTopicGroupCreatorList(joke.getTopicGroups()));
        return jokeCreatorDto;
    }

    JokePagination getJokePagination() {
        return jokePagination;
    }

    void addJoke(JokeCreatorDto jokeCreatorDto) {
        Joke joke = oldJokeMapper.mapJokeCreatorDtoToJoke(jokeCreatorDto);
        if (jokeCreatorDto.getStructureItemList() != null) {
            Set<Algorithm> structures = jokeCreatorDto.getStructureItemList().stream()
                    .map(structureItemDto -> structureFacade.tryToGetStructureById(structureItemDto.getValue()))
                    .collect(Collectors.toSet());
            joke.setStructures(structures);
        }
        jokeRepository.save(joke);
//        if (jokeCreatorDto.getTopicGroupCreatorList() != null) {
//            List<TopicGroup> topicGroupList = topicGroupFacade.mapTopicGroupCreatorListToTopicGroupList(jokeCreatorDto.getTopicGroupCreatorList(), joke);
//            topicGroupFacade.saveTopicGroupList(topicGroupList);
//        }
//        if (jokeCreatorDto.getJokeBlockCreatorDtoList() != null) {
//            List<JokeBlock> jokeBlockList = jokeBlockFacade.extractJokeBlockList(jokeCreatorDto.getJokeBlockCreatorDtoList(), joke);
//            jokeBlockFacade.saveJokeBlockList(jokeBlockList);
//        }
    }

    void editJoke(JokeCreatorDto jokeCreatorDto) {
        Joke joke = jokeFacade.getJokeById(jokeCreatorDto.getId());
        oldJokeMapper.updateJokeFromJokeCreatorDto(jokeCreatorDto, joke);
        Set<Algorithm> structures = Optional.ofNullable(jokeCreatorDto.getStructureItemList())
                .orElse(Collections.emptyList()).stream()
                .map(structureItemDto -> structureFacade.tryToGetStructureById(structureItemDto.getValue()))
                .collect(Collectors.toSet());
        joke.setStructures(structures);
//        List<JokeBlock> jokeBlocks = jokeBlockFacade.extractJokeBlockList(jokeCreatorDto.getJokeBlockCreatorDtoList(), joke);
//        joke.setJokeBlocks(jokeBlocks);
//        List<TopicGroup> topicGroupList = topicGroupFacade.extractTopicGroupList(jokeCreatorDto.getTopicGroupCreatorList(), joke);
//        joke.setTopicGroups(topicGroupList);
        jokeRepository.save(joke);
    }

    void updatePagination(JokePagination jokePagination){
        this.jokePagination.setCurrentPage(jokePagination.getCurrentPage());
        this.jokePagination.setTotalItems(jokePagination.getTotalItems());
        this.jokePagination.setTotalPages(jokePagination.getTotalPages());
        this.jokePagination.setPageSize(jokePagination.getPageSize());
    }

    void deleteJoke(Long id) {
        jokeRepository.delete(jokeFacade.getJokeById(id));
    }

    void rateJoke(JokeRateDto jokeRateDto) {
        Joke joke = jokeFacade.getJokeById(jokeRateDto.getJokeId());
        if (joke.getRate() == null) {
            joke.setRate(new Rate());
        }
        Float oldRate = joke.getRate().getValue();
        Short count = Optional.ofNullable(joke.getRate().getCount()).orElse((short) 0);
        Rate newRate = Rate.builder()
                .count((short) (count + 1))
                .value((oldRate * count + jokeRateDto.getRate()) / (count + 1))
                .build();
        joke.setRate(newRate);
        jokeRepository.save(joke);
    }

    public void resetJokeRate(Long id) {
        Joke joke = jokeFacade.getJokeById(id);
        joke.setRate(new Rate());
        jokeRepository.save(joke);
    }

    public JokePresenterDto getJokePresenterById(Long id) {
        return jokeMapper.mapJokeToJokePresenterDto(jokeFacade.getJokeById(id));
    }

}
