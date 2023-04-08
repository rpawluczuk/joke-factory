package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.diagram.DiagramBlock;
import springapp.jokefactory.algorithm.diagram.DiagramFacade;
import springapp.jokefactory.algorithm.diagram.DiagramBlockPresenterDto;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.algorithm.dto.AlgorithmPresenterDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
class AlgorithmService {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private AlgorithmPagination algorithmPagination;

    @Autowired
    private StructureFacade structureFacade;

    @Autowired
    private DiagramFacade diagramFacade;

    @Autowired
    private JokeFacade jokeFacade;

    @Autowired
    private AlgorithmMapper algorithmMapper;

//    StructureCreatorDto getStructureCreatorDto(Long id) {
//        Structure structure = structureFacade.tryToGetStructureById(id);
//        return structureMapper.mapStructureToStructureCreatorDto(structure);
//    }

    Iterable<AlgorithmPresenterDto> getAlgorithmPresenterList() {
        PageRequest pageRequest = PageRequest.of(algorithmPagination.getCurrentPage(), algorithmPagination.getPageSize(),
                Sort.Direction.DESC, "dateCreated");
        Page<Algorithm> algorithmPage = algorithmRepository.findAll(pageRequest);
        algorithmPagination.setTotalPages(algorithmPage.getTotalPages());
        algorithmPagination.setTotalItems(algorithmPage.getTotalElements());
        return algorithmPage.getContent().stream()
                .map(algorithmMapper::mapAlgorithmToAlgorithmPresenterDto)
                .collect(Collectors.toList());
    }

    Iterable<DiagramBlockPresenterDto> getAlgorithmDiagram(Long algorithmId){
        return diagramFacade.getAlgorithmDiagram(algorithmId);
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
//
//    Iterable<StructureItemDto> getStructureItemList() {
//        return structureRepository.findAll().stream()
//                .map(structureMapper::mapStructureToStructureItemDto)
//                .collect(Collectors.toList());
//    }
//
//    Iterable<StructureItemDto> getStructureItemListByJokeID(Long jokeID) {
//        return structureRepository.findStructuresByJokeID(jokeID).stream()
//                .map(structureMapper::mapStructureToStructureItemDto)
//                .collect(Collectors.toList());
//    }
//
//    StructurePagination getStructurePagination() {
//        return structurePagination;
//    }
//
//    void addStructure(StructureCreatorDto structureCreatorDto) {
//        Structure structure = structureMapper.mapStructureCreatorDtoToStructure(structureCreatorDto);
//        List<StructureBlock> structureBlockList =
//                structureBlockFacade.extractStructureBlockList(structureCreatorDto.getStructureBlockCreatorDtoList(), structure);
//        structure.setStructureBlockScheme(structureBlockList);
//        structureRepository.save(structure);
//    }
//
//    void editStructure(StructureCreatorDto structureCreatorDto) {
//        Structure structure = structureFacade.tryToGetStructureById(structureCreatorDto.getId());
//        Structure updatedStructure = structureMapper.updateStructure(structure, structureCreatorDto);
//        List<StructureBlock> structureBlockList = structureBlockFacade.getStructureBlocksByStructure(structure.getId());
//        List<StructureBlock> updatedStructureBlockList = structureBlockFacade.extractUpdatedStructureBlockList(
//                structureCreatorDto.getStructureBlockCreatorDtoList(), structureBlockList, structure);
//        updatedStructure.setStructureBlockScheme(updatedStructureBlockList);
//        structureRepository.save(updatedStructure);
//    }
//
//    void updateStructurePagination(StructurePagination structurePagination) {
//        this.structurePagination.setCurrentPage(structurePagination.getCurrentPage());
//        this.structurePagination.setTotalItems(structurePagination.getTotalItems());
//        this.structurePagination.setTotalPages(structurePagination.getTotalPages());
//        this.structurePagination.setPageSize(structurePagination.getPageSize());
//    }
//
//    void deleteStructure(Long id) {
//        Structure structureToDelete = structureFacade.tryToGetStructureById(id);
//        jokeFacade.removeStructureFromJokes(structureToDelete);
//        structureRepository.delete(structureToDelete);
//    }
}
