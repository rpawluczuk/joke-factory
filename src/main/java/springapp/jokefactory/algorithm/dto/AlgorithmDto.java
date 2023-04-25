package springapp.jokefactory.algorithm.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockDto;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class AlgorithmDto {

    private Long id;
    private String name;
    private String description;
    private Timestamp dateCreated;
    private List<DiagramBlockDto> diagramBlockList;
}
