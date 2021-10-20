package springapp.jokefactory.topicgroup;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.categorization.Categorization;
import springapp.jokefactory.categorization.CategorizationFacade;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.topicgroup.dto.TopicGroupCreatorDto;
import springapp.jokefactory.topicgroup.dto.TopicGroupPresenterDto;

@Mapper(componentModel = "spring")
abstract class TopicGroupMapper {

    @Autowired
    protected CategorizationFacade categorizationFacade;

    @Autowired
    protected TopicFacade topicFacade;

    @Mapping(target = "id", source = "topicGroupCreatorDto.id")
    @Mapping(target = "joke", source = "joke")
    @Mapping(target = "categorization", source = "topicGroupCreatorDto.categorizationCreator.id", qualifiedByName = "extractCategorization")
    @Mapping(target = "connectingTopic", source = "topicGroupCreatorDto.connectingTopicItem", qualifiedByName = "extractTopic")
    @Mapping(target = "ostensibleTopic", source = "topicGroupCreatorDto.ostensibleTopicItem", qualifiedByName = "extractTopic")
    @Mapping(target = "comedyTopic", source = "topicGroupCreatorDto.comedyTopicItem", qualifiedByName = "extractTopic")
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    abstract TopicGroup mapTopicGroupCreatorDtoToTopicGroup(TopicGroupCreatorDto topicGroupCreatorDto, Joke joke);

    @Mapping(target = "categorizationPresenter", source = "categorization", qualifiedByName = "extractCategorizationPresenter")
    @Mapping(target = "connectingTopicItem", source = "connectingTopic", qualifiedByName = "extractTopicItem")
    @Mapping(target = "ostensibleTopicItem", source = "ostensibleTopic", qualifiedByName = "extractTopicItem")
    @Mapping(target = "comedyTopicItem", source = "comedyTopic", qualifiedByName = "extractTopicItem")
    abstract TopicGroupPresenterDto mapTopicGroupToTopicGroupPresenterDto(TopicGroup topicGroup);

    @Mapping(target = "categorizationCreator", source = "categorization", qualifiedByName = "extractCategorizationCreator")
    @Mapping(target = "connectingTopicItem", source = "connectingTopic", qualifiedByName = "extractTopicItem")
    @Mapping(target = "ostensibleTopicItem", source = "ostensibleTopic", qualifiedByName = "extractTopicItem")
    @Mapping(target = "comedyTopicItem", source = "comedyTopic", qualifiedByName = "extractTopicItem")
    abstract TopicGroupCreatorDto mapTopicGroupToTopicGroupCreatorDto(TopicGroup topicGroup);

    @Named("extractCategorization")
    protected Categorization extractCategorization(Long id) {
        return categorizationFacade.tryToGetCategorizationById(id);
    }

    @Named("extractCategorizationPresenter")
    protected CategorizationPresenterDto extractCategorizationPresenter(Categorization categorization) {
        return categorizationFacade.mapCategorizationToCategorizationPresenterDto(categorization);
    }

    @Named("extractCategorizationCreator")
    protected CategorizationCreatorDto extractCategorizationCreator(Categorization categorization) {
        return categorizationFacade.mapCategorizationToCategorizationCreatorDto(categorization);
    }

    @Named("extractTopic")
    protected Topic extractTopic(TopicItemDto topicItemDto) {
        return topicFacade.tryToGetTopicByTopicItem(topicItemDto).orElse(null);
    }

    @Named("extractTopicItem")
    protected TopicItemDto extractTopicItem(Topic topic) {
        return topicFacade.mapTopicToTopicItemDto(topic);
    }
}
