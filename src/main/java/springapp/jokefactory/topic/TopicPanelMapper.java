package springapp.jokefactory.topic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import springapp.jokefactory.topic.dto.TopicDto;
import springapp.jokefactory.topic.dto.TopicPackDto;
import springapp.jokefactory.topic.dto.TopicPageDto;
import springapp.jokefactory.topic.dto.TopicPanelDto;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
abstract class TopicPanelMapper {

    @Autowired
    protected TopicFacade topicFacade;

    private final TopicMapper topicMapper = Mappers.getMapper(TopicMapper.class);

    TopicPanelDto mapTopicPanelToDto(TopicPanel topicPanel) {
        TopicDto initialTopic = topicMapper.mapTopicToTopicDto(topicPanel.getInitialTopic());
        List<TopicPackDto> topicPackList = topicPanel.getTopicPackList().stream()
                .map(this::mapTopicPackToDto)
                .collect(Collectors.toList());

        return TopicPanelDto.builder()
                .initialTopic(initialTopic)
                .topicPackList(topicPackList)
                .build();
    }

    @Mapping(target = "topicParent", source = "topicParent", qualifiedByName = "mapTopicToDto")
    @Mapping(target = "topicPage", source = "topicPage", qualifiedByName = "mapTopicPageToDto")
    TopicPackDto mapTopicPackToDto(TopicPack topicPack) {
        TopicDto topicParent = topicMapper.mapTopicToTopicDto(topicPack.getTopicParent());
        TopicPageDto topicPage = mapPageToDto(topicPack.getTopicPage());
        return TopicPackDto.builder()
                .topicParent(topicParent)
                .topicPage(topicPage)
                .build();
    }

    TopicPageDto mapPageToDto(Page<Topic> topicPage) {
        List<TopicDto> content = topicPage.getContent().stream()
                .map(topicMapper::mapTopicToTopicDto)
                .collect(Collectors.toList());

        return TopicPageDto.builder()
                .content(content)
                .number(topicPage.getNumber())
                .size(topicPage.getSize())
                .totalPages(topicPage.getTotalPages())
                .totalElements(topicPage.getTotalElements())
                .build();
    }
}
