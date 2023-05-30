package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.algorithmblock.AlgorithmBlockFacade;
import springapp.jokefactory.algorithm.algorithmblock.dto.AlgorithmBlockDto;
import springapp.jokefactory.algorithm.dto.AlgorithmItemDto;
import springapp.jokefactory.joke.jokeblock.JokeBlockFacade;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.algorithm.dto.AlgorithmDto;

import java.util.stream.Collectors;

@Service
class AlgorithmService {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private AlgorithmPagination algorithmPagination;

    @Autowired
    private springapp.jokefactory.algorithm.AlgorithmFacade algorithmFacade;

    @Autowired
    private AlgorithmBlockFacade algorithmBlockFacade;

    @Autowired
    private JokeBlockFacade jokeDiagramFacade;

    @Autowired
    private JokeFacade jokeFacade;

    @Autowired
    private AlgorithmMapper algorithmMapper;

    AlgorithmDto getAlgorithmDto(Long id) {
        Algorithm algorithm = algorithmFacade.getAlgorithmById(id);
        return algorithmMapper.mapAlgorithmToDto(algorithm);
    }

    Iterable<AlgorithmDto> getAlgorithmPresenterList() {
        PageRequest pageRequest = PageRequest.of(algorithmPagination.getCurrentPage(), algorithmPagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Algorithm> algorithmPage = algorithmRepository.findAll(pageRequest);
        algorithmPagination.setTotalPages(algorithmPage.getTotalPages());
        algorithmPagination.setTotalItems(algorithmPage.getTotalElements());
        return algorithmPage.getContent().stream()
                .map(algorithmMapper::mapAlgorithmToDto)
                .collect(Collectors.toList());
    }

    Iterable<AlgorithmBlockDto> getAlgorithmBlockList(Long algorithmId) {
        return algorithmBlockFacade.getAlgorithmBlockList(algorithmId);
    }

//    Iterable<StructurePresenterDto> getStructurePresenterListByName(String name) {
//        PageRequest pageRequest = PageRequest.of(structurePagination.getCurrentPage(), structurePagination.getPageSize(),
//                Sort.Direction.DESC, "dateCreated");
//        Page<Structure> structurePage = structureRepository.findStructureByNameContaining(name, pageRequest);
//        structurePagination.setTotalPages(structurePage.getTotalPages());
//        structurePagination.setTotalItems(structurePage.getTotalElements());
//        return structurePage.getContent().stream()
//                .map(structureMapper::mapStructureToStructurePresenterDto)
//                .collect(Collectors.toList());
//    }

    public Iterable<AlgorithmItemDto> getAlgorithmItemList() {
        return algorithmRepository.findAll().stream()
                .map(algorithmMapper::mapToAlgorithmItemDto)
                .collect(Collectors.toList());
    }

    Iterable<AlgorithmItemDto> getAlgorithmItemListByJokeID(Long jokeID) {
        return algorithmRepository.findAlgorithmsByJokeID(jokeID).stream()
                .map(algorithmMapper::mapToAlgorithmItemDto)
                .collect(Collectors.toList());
    }

    AlgorithmPagination getAlgorithmPagination() {
        return algorithmPagination;
    }

    void addAlgorithm(AlgorithmDto algorithmDto) {
        Algorithm algorithm = algorithmMapper.mapDtoToAlgorithm(algorithmDto);
        algorithm = algorithmRepository.save(algorithm);
        algorithmBlockFacade.saveAlgorithmBlockList(algorithmDto.getDiagramBlockList(), algorithm);
    }

    void editAlgorithm(AlgorithmDto algorithmDto) {
        Algorithm algorithm = algorithmFacade.getAlgorithmById(algorithmDto.getId());
        Algorithm updatedAlgorithm = algorithmMapper.updateAlgorithm(algorithm, algorithmDto);
        algorithm = algorithmRepository.save(updatedAlgorithm);
        algorithmBlockFacade.updateAlgorithmList(algorithmDto.getDiagramBlockList(), algorithm);
    }

    void updateAlgorithmPagination(AlgorithmPagination algorithmPagination) {
        this.algorithmPagination.setCurrentPage(algorithmPagination.getCurrentPage());
        this.algorithmPagination.setTotalItems(algorithmPagination.getTotalItems());
        this.algorithmPagination.setTotalPages(algorithmPagination.getTotalPages());
        this.algorithmPagination.setPageSize(algorithmPagination.getPageSize());
    }

    void deleteAlgorithm(Long id) {
        Algorithm algorithmToDelete = algorithmFacade.getAlgorithmById(id);
//        jokeFacade.removeStructureFromJokes(structureToDelete);
        algorithmRepository.delete(algorithmToDelete);
    }
}
