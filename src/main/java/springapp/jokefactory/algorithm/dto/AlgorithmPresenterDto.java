package springapp.jokefactory.algorithm.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AlgorithmPresenterDto {

    private Long id;
    private String name;
    private String description;
    private Timestamp dateCreated;
}
