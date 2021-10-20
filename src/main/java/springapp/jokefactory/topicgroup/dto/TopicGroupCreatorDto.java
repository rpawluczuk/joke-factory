package springapp.jokefactory.topicgroup.dto;

import lombok.Data;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
public class TopicGroupCreatorDto {

    private Long id;
    private CategorizationCreatorDto categorizationCreator;
    private TopicItemDto connectingTopicItem;
    private TopicItemDto comedyTopicItem;
    private TopicItemDto ostensibleTopicItem;
}
