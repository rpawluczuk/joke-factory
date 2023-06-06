package springapp.jokefactory.algorithm.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.algorithm.algorithmblock.dto.AlgorithmBlockDto;

import java.util.List;

@Data
@Builder
public class AlgorithmDto {

    private Long id;
    private String name;
    private String description;
    private String dateCreated;
    private List<AlgorithmBlockDto> diagramBlockList;
}
