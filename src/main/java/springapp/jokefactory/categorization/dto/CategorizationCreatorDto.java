package springapp.jokefactory.categorization.dto;

import lombok.Data;
import springapp.jokefactory.topic.dto.TopicDto;

@Data
public class CategorizationCreatorDto {

    private Long id;
    private String name;
    private TopicDto connectingCategory;
    private String questions;
    private TopicDto ostensibleCategory;
    private TopicDto comedyCategory;
}
