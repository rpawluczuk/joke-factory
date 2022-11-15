package springapp.jokefactory.categorization.dto;

import lombok.Data;
import springapp.jokefactory.topic.panel.TopicBlockDto;

@Data
public class CategorizationCreatorDto {

    private Long id;
    private String name;
    private TopicBlockDto connectingCategory;
    private String questions;
    private TopicBlockDto ostensibleCategory;
    private TopicBlockDto comedyCategory;
}
