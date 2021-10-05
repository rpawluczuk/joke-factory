package springapp.jokefactory.structure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springapp.jokefactory.structure.dto.StructureCreatorDto;
import springapp.jokefactory.structure.dto.StructureItemDto;
import springapp.jokefactory.structure.dto.StructurePresenterDto;

@Mapper
public interface StructureMapper {

    StructurePresenterDto mapStructureToStructurePresenterDto(Structure structure);

    @Mapping(target = "text", source = "structure.name")
    StructureItemDto mapStructureToStructureItemDto(Structure structure);

    Structure mapStructureCreatorDtoToStructure(StructureCreatorDto structureCreatorDto);

    StructureCreatorDto mapStructureToStructureCreatorDto(Structure structure);

    @Mapping(target = "id", source = "structure.id")
    @Mapping(target = "name", source = "structureCreatorDto.name")
    @Mapping(target = "description", source = "structureCreatorDto.description")
    @Mapping(target = "structureBlockScheme", ignore = true)
    Structure updateStructure(Structure structure, StructureCreatorDto structureCreatorDto);
}
