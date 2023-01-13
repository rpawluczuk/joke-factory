package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicDto;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.dto.TopicItemDto;


import java.util.List;
import java.util.stream.Collectors;

@Service
class TopicPanelMapper {

    @Autowired
    TopicFacade topicFacade;

    TopicPanelDto mapTopicPanelToDto(TopicPanel topicPanel) {
        TopicBlockDto initialTopic = mapTopicBlockToDto(topicPanel.getInitialTopicBlock());
        List<TopicPackDto> topicPackList = topicPanel.getTopicPackList().stream()
                .map(this::mapTopicPackToDto)
                .collect(Collectors.toList());

        return TopicPanelDto.builder()
                .initialTopic(initialTopic)
                .topicPackList(topicPackList)
                .build();
    }

    TopicPackDto mapTopicPackToDto(TopicPack topicPack) {
        TopicBlockDto topicParent = mapTopicBlockToDto(topicPack.getTopicBlockParent());
        TopicPageDto topicPage = mapPageToDto(topicPack.getTopicBlockPage());
        TopicItemDto categoryFilter = null;
        if (topicPack.getCategoryFilter() != null) {
            categoryFilter = topicFacade.mapTopicDtoToTopicItemDto(topicPack.getCategoryFilter());
        }

        return TopicPackDto.builder()
                .topicBlockParent(topicParent)
                .topicBlockPage(topicPage)
                .categoryFilter(categoryFilter)
                .isAnySelection(topicPack.isAnySelection())
                .build();
    }

    TopicPageDto mapPageToDto(Page<TopicBlock> topicPage) {
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

    TopicBlockDto mapTopicBlockToDto(TopicBlock topicBlock) {
        return TopicBlockDto.builder()
                .id(topicBlock.getTopic().getId())
                .name(topicBlock.getTopic().getName())
                .isCategory(topicBlock.getTopic().isCategory())
                .isSelected(topicBlock.isSelected())
                .isSecondParent(topicBlock.isSecondParent())
                .topicPackIndex(topicBlock.getTopicPackIndex())
                .build();
    }

    Topic mapTopicBlockDtoToTopic(TopicBlockDto topicBlockDto) {
        return Topic.builder()
                .name(topicBlockDto.getName())
                .build();
    }

    TopicDto mapTopicBlockDtoToTopicDto(TopicBlockDto topicBlockDto) {
        return TopicDto.builder()
                .name(topicBlockDto.getName())
                .build();
    }

    TopicBlock mapTopicToTopicBlock(TopicDto topicDto) {
        return TopicBlock.builder()
                .topic(topicDto)
                .build();
    }

    Page<TopicBlock> mapTopicDtoPageToTopicBlockPage(Page<TopicDto> topicPage, PageRequest pageRequest) {
        return new PageImpl<>(
                topicPage.getContent().stream()
                        .map(topicDto -> TopicBlock.builder().topic(topicDto).build())
                        .collect(Collectors.toList()),
                pageRequest, topicPage.getTotalElements()
        );
    }
}
