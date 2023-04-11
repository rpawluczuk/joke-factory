package springapp.jokefactory.algorithm.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AlgorithmDto {

    private Long id;
    private String name;
    private String description;
    private Timestamp dateCreated;
}
