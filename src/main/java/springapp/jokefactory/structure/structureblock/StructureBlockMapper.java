package springapp.jokefactory.structure.structureblock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.structure.Structure;

@Mapper
public interface StructureBlockMapper {

    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
    @Mapping(target = "structure", source = "structure")
    StructureBlock mapStructureBlockCreatorDtoToStructureBlock(StructureBlockCreatorDto structureBlockCreatorDto, Structure structure);

    StructureBlockPresenterDto mapStructureBlockToStructureBlockPresenterDto(StructureBlock structureBlock);

    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
    @Mapping(target = "title", source = "structureBlockCreatorDto.title")
    @Mapping(target = "position", source = "structureBlockCreatorDto.position")
    StructureBlock updateStructureBlock(StructureBlock structureBlock, StructureBlockCreatorDto structureBlockCreatorDto);
}
