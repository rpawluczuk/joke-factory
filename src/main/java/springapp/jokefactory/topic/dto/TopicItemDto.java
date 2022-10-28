package springapp.jokefactory.topic.dto;

import lombok.Data;

@Data
public class TopicItemDto {

    private Long value;
    private String label;

    public TopicItemDto(String label, Long value) {
        this.label = label;
        this.value = value;
    }
}
