package springapp.jokefactory.categorization;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationItemDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.dto.TopicCreatorDto;

@Mapper(componentModel = "spring")
abstract class CategorizationMapper {

    @Autowired
    protected TopicFacade topicFacade;

    @Mapping(target = "connectingCategory", source = "connectingCategory.name")
    @Mapping(target = "ostensibleCategory", source = "ostensibleCategory.name")
    @Mapping(target = "comedyCategory", source = "comedyCategory.name")
    abstract CategorizationPresenterDto mapCategorizationToCategorizationPresenterDto(Categorization categorization);

    @Mapping(target = "text", source = "name")
    abstract CategorizationItemDto mapCategorizationToCategorizationItemDto(Categorization categorization);

    @Mapping(target = "text", source = "name")
    abstract CategorizationItemDto mapCategorizationCreatorToCategorizationItem(CategorizationCreatorDto categorizationCreatorDto);

    @Mapping(target = "connectingCategory", source = "categorization.connectingCategory", qualifiedByName = "extractTopicCreator")
    @Mapping(target = "ostensibleCategory", source = "categorization.ostensibleCategory", qualifiedByName = "extractTopicCreator")
    @Mapping(target = "comedyCategory", source = "categorization.comedyCategory", qualifiedByName = "extractTopicCreator")
    abstract CategorizationCreatorDto mapCategorizationToCategorizationCreatorDto(Categorization categorization);

    @Mapping(target = "connectingCategory", ignore = true)
    @Mapping(target = "ostensibleCategory", ignore = true)
    @Mapping(target = "comedyCategory", ignore = true)
    abstract void updateCategorizationFromCategorizationCreatorDto(CategorizationCreatorDto categorizationCreatorDto, @MappingTarget Categorization categorization);

    @Named("extractTopicCreator")
    protected TopicCreatorDto extractTopicCreator(Topic topic) {
        return topicFacade.mapTopicToTopicCreatorDto(topic);
    }
}
