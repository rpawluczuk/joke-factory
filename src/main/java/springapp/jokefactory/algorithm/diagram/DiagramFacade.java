package springapp.jokefactory.algorithm.diagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockPresenterDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagramFacade {

    @Autowired
    private DiagramRepository diagramRepository;

    @Autowired
    private DiagramMapper diagramMapper;

//    public DiagramBlock tryToGetStructureBlockByID(Long id) {
//        return structureBlockRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("No structure block found with id: " + id));
//    }

    public List<DiagramBlockPresenterDto> getAlgorithmDiagram(long algorithmId) {
        return diagramRepository.findDiagramBlocksByAlgorithm_IdOrderByPosition(algorithmId).stream()
                .map(diagramMapper::mapDiagramBlockToPresenterDto)
                .collect(Collectors.toList());
    }

//    public List<DiagramBlock> getStructureBlocksByJoke(long jokeId) {
//        return structureBlockRepository.findStructureBlocksByJoke(jokeId);
//    }
//
//    public List<DiagramBlock> extractStructureBlockList(List<StructureBlockCreatorDto> structureBlockCreatorList,
//                                                        Algorithm structure) {
//        return structureBlockCreatorList.stream()
//                .map(structureBlockCreatorDto ->
//                        structureBlockMapper.mapStructureBlockCreatorDtoToStructureBlock(structureBlockCreatorDto, structure))
//                .collect(Collectors.toList());
//    }
//
//    public List<DiagramBlock> extractUpdatedStructureBlockList(List<StructureBlockCreatorDto> structureBlockCreatorDtoList,
//                                                               List<DiagramBlock> structureBlockList,
//                                                               Algorithm structure) {
//        return IntStream.range(0, structureBlockCreatorDtoList.size())
//                .mapToObj(index -> {
//                    if (index < structureBlockList.size()){
//                        return structureBlockMapper.updateStructureBlock(structureBlockList.get(index), structureBlockCreatorDtoList.get(index));
//                    }
//                    return structureBlockMapper.mapStructureBlockCreatorDtoToStructureBlock(structureBlockCreatorDtoList.get(index), structure);
//                })
//                .collect(Collectors.toList());
//    }
//
//    public DiagramBlockPresenterDto extractStructureBlockPresenterDto(DiagramBlock structureBlock) {
//        return structureBlockMapper.mapStructureBlockToStructureBlockPresenterDto(structureBlock);
//    }
}
