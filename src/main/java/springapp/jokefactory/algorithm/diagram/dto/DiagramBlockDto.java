package springapp.jokefactory.algorithm.diagram.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiagramBlockDto {

    private Long id;
    private String title;
    private String description;
    private int position;
}
