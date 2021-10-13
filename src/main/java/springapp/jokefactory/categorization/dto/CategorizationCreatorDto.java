package springapp.jokefactory.categorization.dto;

import lombok.Data;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
public class CategorizationCreatorDto {

    private Long id;
    private String name;
    private TopicItemDto baseCategory;
    private String questions;
    private TopicItemDto linkedCategory;
}
