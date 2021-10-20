package springapp.jokefactory.topicgroup.dto;

import lombok.Data;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
public class TopicGroupCreatorDto {

    private Long id;
    private CategorizationCreatorDto categorization;
    private TopicItemDto connectingTopic;
    private TopicItemDto comedyTopic;
    private TopicItemDto ostensibleTopic;
}
