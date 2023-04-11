package springapp.jokefactory.algorithm.dto;

import lombok.Data;
import springapp.jokefactory.structureblock.dto.StructureBlockCreatorDto;

import java.util.List;

@Data
public class AlgorithmCreatorDto {

    private Long id;
    private String name;
    private String description;
    private List<StructureBlockCreatorDto> structureBlockCreatorDtoList;
}
