package springapp.jokefactory.structure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StructureMapper {

    StructurePresenterDto mapStructureToStructurePresenterDto(Structure structure);

    @Mapping(target = "text", source = "structure.name")
    StructureItemDto mapStructureToStructureItemDto(Structure structure);
}
