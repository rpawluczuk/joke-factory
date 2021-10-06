package springapp.jokefactory.structureblock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.structureblock.dto.StructureBlockCreatorDto;
import springapp.jokefactory.structureblock.dto.StructureBlockPresenterDto;

@Mapper(componentModel = "spring")
abstract class StructureBlockMapper {

    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
    @Mapping(target = "structure", source = "structure")
    abstract StructureBlock mapStructureBlockCreatorDtoToStructureBlock(StructureBlockCreatorDto structureBlockCreatorDto, Structure structure);

    abstract StructureBlockCreatorDto mapStructureBlockToStructureBlockCreatorDto(StructureBlock structureBlock);

    abstract StructureBlockPresenterDto mapStructureBlockToStructureBlockPresenterDto(StructureBlock structureBlock);

    @Mapping(target = "id", source = "structureBlockCreatorDto.id")
    @Mapping(target = "description", source = "structureBlockCreatorDto.description")
    @Mapping(target = "title", source = "structureBlockCreatorDto.title")
    @Mapping(target = "position", source = "structureBlockCreatorDto.position")
    abstract StructureBlock updateStructureBlock(StructureBlock structureBlock, StructureBlockCreatorDto structureBlockCreatorDto);
}
