package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.dto.TopicItemDto;


import java.util.List;
import java.util.stream.Collectors;

@Service
class TopicPanelMapper {

    @Autowired
    TopicFacade topicFacade;

    TopicPanelDto mapTopicPanelToDto(TopicPanel topicPanel) {
        TopicBlockDto initialTopic = mapTopicBlockToDto(topicPanel.getInitialTopic());
        List<TopicPackDto> topicPackList = topicPanel.getTopicPackList().stream()
                .map(this::mapTopicPackToDto)
                .collect(Collectors.toList());

        return TopicPanelDto.builder()
                .initialTopic(initialTopic)
                .topicPackList(topicPackList)
                .build();
    }

    TopicPackDto mapTopicPackToDto(TopicPack topicPack) {
        TopicBlockDto topicParent = mapTopicBlockToDto(topicPack.getTopicParent());
        TopicPageDto topicPage = mapPageToDto(topicPack.getTopicPage());
        TopicItemDto categoryFilter = topicFacade.mapTopicToTopicItemDto(topicPack.getCategoryFilter());
        return TopicPackDto.builder()
                .topicParent(topicParent)
                .topicPage(topicPage)
                .categoryFilter(categoryFilter)
                .build();
    }

    TopicPageDto mapPageToDto(Page<Topic> topicPage) {
        List<TopicBlockDto> content = topicPage.getContent().stream()
                .map(this::mapTopicBlockToDto)
                .collect(Collectors.toList());

        return TopicPageDto.builder()
                .content(content)
                .number(topicPage.getNumber())
                .size(topicPage.getSize())
                .totalPages(topicPage.getTotalPages())
                .totalElements(topicPage.getTotalElements())
                .build();
    }

    TopicBlockDto mapTopicBlockToDto(Topic topic) {
        return TopicBlockDto.builder()
                .id(topic.getId())
                .isCategory(topic.isCategory())
                .name(topic.getName())
                .build();
    }
}
