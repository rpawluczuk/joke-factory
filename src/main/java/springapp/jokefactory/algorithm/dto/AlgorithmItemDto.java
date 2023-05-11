package springapp.jokefactory.algorithm.dto;

import lombok.Data;

@Data
public class AlgorithmItemDto {

    private Long value;
    private String label;

    public AlgorithmItemDto(String label, Long value) {
        this.label = label;
        this.value = value;
    }
}
