package springapp.jokefactory.algorithm;

import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.dto.AlgorithmDto;

@Service
class AlgorithmMapper {

    AlgorithmDto mapAlgorithmToDto(Algorithm algorithm) {
        return AlgorithmDto.builder()
                .id(algorithm.getId())
                .name(algorithm.getName())
                .description(algorithm.getDescription())
                .build();
    }

    Algorithm mapDtoToAlgorithm(AlgorithmDto algorithmDto) {
        return Algorithm.builder()
                .name(algorithmDto.getName())
                .description(algorithmDto.getDescription())
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
