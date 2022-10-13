package springapp.jokefactory.structure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.structure.dto.StructureCreatorDto;
import springapp.jokefactory.structure.dto.StructureItemDto;
import springapp.jokefactory.structure.dto.StructurePresenterDto;

@Mapper(componentModel = "spring")
abstract class StructureMapper {

    abstract StructurePresenterDto mapStructureToStructurePresenterDto(Structure structure);

    @Mapping(target = "value", source = "structure.name")
    abstract StructureItemDto mapStructureToStructureItemDto(Structure structure);

    abstract Structure mapStructureCreatorDtoToStructure(StructureCreatorDto structureCreatorDto);

    abstract StructureCreatorDto mapStructureToStructureCreatorDto(Structure structure);

    @Mapping(target = "id", source = "structure.id")
    @Mapping(target = "name", source = "structureCreatorDto.name")
    @Mapping(target = "description", source = "structureCreatorDto.description")
    @Mapping(target = "structureBlockScheme", ignore = true)
    abstract Structure updateStructure(Structure structure, StructureCreatorDto structureCreatorDto);
}
