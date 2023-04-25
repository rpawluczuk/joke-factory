package springapp.jokefactory.algorithm.diagram;

import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockDto;

@Service
class DiagramMapper {

//    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
//    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
//    @Mapping(target = "structure", source = "structure")
//    abstract DiagramBlock mapStructureBlockCreatorDtoToStructureBlock(StructureBlockCreatorDto structureBlockCreatorDto, Algorithm structure);
//
//    abstract StructureBlockCreatorDto mapStructureBlockToStructureBlockCreatorDto(DiagramBlock structureBlock);

    DiagramBlockDto mapDiagramBlockToPresenterDto(DiagramBlock diagramBlock) {
        return DiagramBlockDto.builder()
                .id(diagramBlock.getId())
                .title(diagramBlock.getTitle())
                .description(diagramBlock.getDescription())
                .position(diagramBlock.getPosition())
                .build();
    }

    DiagramBlock mapDtoToDiagramBlock(DiagramBlockDto diagramBlockPresenterDto) {
        return DiagramBlock.builder()
                .id(diagramBlockPresenterDto.getId())
                .title(diagramBlockPresenterDto.getTitle())
                .description(diagramBlockPresenterDto.getDescription())
                .position(diagramBlockPresenterDto.getPosition())
                .build();
    }

    public DiagramBlock updateDiagramBlock(DiagramBlock diagramBlock, DiagramBlockDto diagramBlockDto) {
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
