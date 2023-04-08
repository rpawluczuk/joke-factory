package springapp.jokefactory.algorithm.diagram;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.structureblock.dto.StructureBlockCreatorDto;

@Service
class DiagramMapper {

//    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
//    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
//    @Mapping(target = "structure", source = "structure")
//    abstract DiagramBlock mapStructureBlockCreatorDtoToStructureBlock(StructureBlockCreatorDto structureBlockCreatorDto, Algorithm structure);
//
//    abstract StructureBlockCreatorDto mapStructureBlockToStructureBlockCreatorDto(DiagramBlock structureBlock);

    DiagramBlockPresenterDto mapDiagramBlockToPresenterDto(DiagramBlock diagramBlock) {
        return DiagramBlockPresenterDto.builder()
                .id(diagramBlock.getId())
                .title(diagramBlock.getTitle())
                .description(diagramBlock.getDescription())
                .position(diagramBlock.getPosition())
                .build();
    }

//    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
//    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
//    @Mapping(target = "title", source = "structureBlockCreatorDto.title")
//    @Mapping(target = "position", source = "structureBlockCreatorDto.position")
//    abstract DiagramBlock updateStructureBlock(DiagramBlock structureBlock, StructureBlockCreatorDto structureBlockCreatorDto);
}
