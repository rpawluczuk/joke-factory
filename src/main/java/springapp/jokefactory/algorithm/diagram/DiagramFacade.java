package springapp.jokefactory.algorithm.diagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockDto;

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

    public List<DiagramBlockDto> getAlgorithmDiagram(long algorithmId) {
        return diagramRepository.findDiagramBlocksByAlgorithm_IdOrderByPosition(algorithmId).stream()
                .map(diagramMapper::mapDiagramBlockToPresenterDto)
                .collect(Collectors.toList());
    }

    public void saveDiagram(List<DiagramBlockDto> diagramBlockDtoList, Algorithm algorithm) {
        diagramBlockDtoList.stream()
                .map(diagramBlockDto ->
                        diagramMapper.mapDtoToDiagramBlock(diagramBlockDto))
                .forEach(diagramBlock -> {
                    diagramBlock.setAlgorithm(algorithm);
                    diagramRepository.save(diagramBlock);
                });
    }

    public void updateDiagram(List<DiagramBlockDto> diagramBlockDtoList, Algorithm algorithm) {
        diagramBlockDtoList.stream()
                .map(diagramBlockDto -> {
                    if (diagramBlockDto.getId() != null){
                        DiagramBlock diagramBlock = getDiagramBlockById(diagramBlockDto.getId());
                        return diagramMapper.updateDiagramBlock(diagramBlock, diagramBlockDto);
                    }
                    return diagramMapper.mapDtoToDiagramBlock(diagramBlockDto);
                })
                .forEach(diagramBlock -> {
                    diagramBlock.setAlgorithm(algorithm);
                    diagramRepository.save(diagramBlock);
                });
    }

    DiagramBlock getDiagramBlockById(Long id) {
        return diagramRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No diagram block found with id: " + id));
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
