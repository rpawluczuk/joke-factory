package springapp.jokefactory.topicgroup.dto;

import lombok.Data;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;
import springapp.jokefactory.topic.dto.TopicItemDto;

@Data
public class TopicGroupPresenterDto {

    private Long id;
    private CategorizationPresenterDto categorizationPresenter;
    private TopicItemDto connectingTopicItem;
    private TopicItemDto comedyTopicItem;
    private TopicItemDto ostensibleTopicItem;
}
