package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.diagram.DiagramFacade;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockDto;
import springapp.jokefactory.algorithm.dto.AlgorithmDto;
import springapp.jokefactory.algorithm.dto.AlgorithmItemDto;

import java.util.List;

@Service
class AlgorithmMapper {

    @Autowired
    DiagramFacade diagramFacade;

    AlgorithmDto mapAlgorithmToDto(Algorithm algorithm) {
        List<DiagramBlockDto> diagramBlockList = diagramFacade.getAlgorithmDiagram(algorithm.getId());
        return AlgorithmDto.builder()
                .id(algorithm.getId())
                .name(algorithm.getName())
                .description(algorithm.getDescription())
                .diagramBlockList(diagramBlockList)
                .build();
    }

    Algorithm mapDtoToAlgorithm(AlgorithmDto algorithmDto) {
        return Algorithm.builder()
                .name(algorithmDto.getName())
                .description(algorithmDto.getDescription())
                .build();
    }

    public Algorithm updateAlgorithm(Algorithm algorithm, AlgorithmDto algorithmDto) {
        algorithm.setName(algorithmDto.getName());
        algorithm.setDescription(algorithmDto.getDescription());
        return algorithm;
    }
    public AlgorithmItemDto mapToAlgorithmItemDto(Algorithm algorithm) {
        return new AlgorithmItemDto(algorithm.getName(), algorithm.getId());
    }
//
//    abstract Structure mapStructureCreatorDtoToStructure(StructureCreatorDto structureCreatorDto);
//
//    abstract StructureCreatorDto mapStructureToStructureCreatorDto(Structure structure);
//
//    @Mapping(target = "id", source = "structure.id")
//    @Mapping(target = "name", source = "structureCreatorDto.name")
//    @Mapping(target = "description", source = "structureCreatorDto.description")
//    @Mapping(target = "structureBlockScheme", ignore = true)
//    abstract Structure updateStructure(Structure structure, StructureCreatorDto structureCreatorDto);
}
