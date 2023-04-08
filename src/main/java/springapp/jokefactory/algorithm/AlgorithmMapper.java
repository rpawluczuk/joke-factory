package springapp.jokefactory.algorithm;

import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.dto.AlgorithmPresenterDto;

@Service
class AlgorithmMapper {

    AlgorithmPresenterDto mapAlgorithmToAlgorithmPresenterDto(Algorithm algorithm) {
        return AlgorithmPresenterDto.builder()
                .id(algorithm.getId())
                .name(algorithm.getName())
                .description(algorithm.getDescription())
                .build();
    }

//    @Mapping(target = "value", source = "structure.name")
//    abstract StructureItemDto mapStructureToStructureItemDto(Structure structure);
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
