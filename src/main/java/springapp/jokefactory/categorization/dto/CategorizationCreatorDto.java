package springapp.jokefactory.categorization.dto;

import lombok.Data;
import springapp.jokefactory.topic.dto.TopicCreatorDto;

@Data
public class CategorizationCreatorDto {

    private Long id;
    private String name;
    private TopicCreatorDto connectingCategory;
    private String questions;
    private TopicCreatorDto ostensibleCategory;
    private TopicCreatorDto comedyCategory;
}
