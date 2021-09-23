package springapp.jokefactory.structure;

import org.mapstruct.Mapper;

@Mapper
public interface StructureMapper {

    StructurePresenterDto mapStructureToStructurePresenterDto(Structure structure);
}
