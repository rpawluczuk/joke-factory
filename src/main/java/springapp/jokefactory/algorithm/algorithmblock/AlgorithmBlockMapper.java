package springapp.jokefactory.algorithm.algorithmblock;

import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.algorithmblock.dto.AlgorithmBlockDto;

@Service
class AlgorithmBlockMapper {

//    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
//    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
//    @Mapping(target = "structure", source = "structure")
//    abstract DiagramBlock mapStructureBlockCreatorDtoToStructureBlock(StructureBlockCreatorDto structureBlockCreatorDto, Algorithm structure);
//
//    abstract StructureBlockCreatorDto mapStructureBlockToStructureBlockCreatorDto(DiagramBlock structureBlock);

    AlgorithmBlockDto mapAlgorithmBlockToPresenterDto(AlgorithmBlock diagramBlock) {
        return AlgorithmBlockDto.builder()
                .id(diagramBlock.getId())
                .title(diagramBlock.getTitle())
                .description(diagramBlock.getDescription())
                .position(diagramBlock.getPosition())
                .build();
    }

    AlgorithmBlock mapDtoToAlgorithmBlock(AlgorithmBlockDto diagramBlockPresenterDto) {
        return AlgorithmBlock.builder()
                .id(diagramBlockPresenterDto.getId())
                .title(diagramBlockPresenterDto.getTitle())
                .description(diagramBlockPresenterDto.getDescription())
                .position(diagramBlockPresenterDto.getPosition())
                .build();
    }

    public AlgorithmBlock updateAlgorithmBlock(AlgorithmBlock diagramBlock, AlgorithmBlockDto diagramBlockDto) {
        diagramBlock.setTitle(diagramBlockDto.getTitle());
        diagramBlock.setDescription(diagramBlockDto.getDescription());
        diagramBlock.setPosition(diagramBlockDto.getPosition());
        return diagramBlock;
    }

//    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
//    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
//    @Mapping(target = "title", source = "structureBlockCreatorDto.title")
//    @Mapping(target = "position", source = "structureBlockCreatorDto.position")
//    abstract DiagramBlock updateStructureBlock(DiagramBlock structureBlock, StructureBlockCreatorDto structureBlockCreatorDto);
}
