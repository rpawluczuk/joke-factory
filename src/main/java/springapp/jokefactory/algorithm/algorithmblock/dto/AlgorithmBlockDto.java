package springapp.jokefactory.algorithm.algorithmblock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlgorithmBlockDto {

    private Long id;
    private String title;
    private String description;
    private int position;
}
