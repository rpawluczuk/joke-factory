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
        TopicBlockDto initialTopic = Depracated_mapTopicBlockToDto(topicPanel.getInitialTopicBlock());
        List<TopicPackDto> topicPackList = topicPanel.getTopicPackList().stream()
                .map(this::mapTopicPackToDto)
                .collect(Collectors.toList());

        return TopicPanelDto.builder()
                .initialTopic(initialTopic)
                .topicPackList(topicPackList)
                .build();
    }

    TopicPackDto mapTopicPackToDto(TopicPack topicPack) {
        TopicBlockDto topicParent = Depracated_mapTopicBlockToDto(topicPack.getTopicBlockParent());
        TopicBlockDto topicSecondParent = Depracated_mapTopicBlockToDto(topicPack.getTopicBlockSecondParent());
        TopicPageDto topicPage = mapPageToDto(topicPack.getTopicBlockPage());
        TopicItemDto categoryFilter = null;
        if (topicPack.getCategoryFilter() != null) {
            categoryFilter = topicFacade.mapTopicDtoToTopicItemDto(topicPack.getCategoryFilter());
        }
        return TopicPackDto.builder()
                .topicBlockParent(topicParent)
                .topicBlockSecondParent(topicSecondParent)
                .categoryFilter(categoryFilter)
                .isAnySelection(topicPack.isAnySelection())
                .topicPackIndex(topicPack.getTopicPackIndex())
                .build();
    }

    TopicPageDto mapPageToDto(Page<TopicBlock> topicPage) {
        List<TopicBlockDto> content = topicPage.getContent().stream()
                .map(this::Depracated_mapTopicBlockToDto)
                .collect(Collectors.toList());

        return TopicPageDto.builder()
                .content(content)
                .number(topicPage.getNumber())
                .size(topicPage.getSize())
                .totalPages(topicPage.getTotalPages())
                .totalElements(topicPage.getTotalElements())
                .build();
    }

    TopicBlockDto Depracated_mapTopicBlockToDto(TopicBlock topicBlock) {
        if (topicBlock == null) return null;
        List<TopicItemDto> categories = null;
        if (!topicBlock.getDeprecated_topic().isCategory()) {
            categories = topicBlock.getDeprecated_topic().getCategories().stream()
                    .map(this::mapTopicDtoToTopicItemDto)
                    .collect(Collectors.toList());
        }
        return TopicBlockDto.builder()
                .id(topicBlock.getDeprecated_topic().getId())
                .name(topicBlock.getDeprecated_topic().getName())
                .isCategory(topicBlock.getDeprecated_topic().isCategory())
                .isSelected(topicBlock.isSelected())
                .isSecondParent(topicBlock.isSecondParent())
                .topicPackIndex(topicBlock.getTopicPackIndex())
                .parentId(topicBlock.getParentId())
                .categories(categories)
                .build();
    }

    Topic toTopic(TopicBlockDto topicBlockDto) {
        return Topic.builder()
                .name(topicBlockDto.getName())
                .build();
    }

    TopicDto mapTopicBlockDtoToTopicDto(TopicBlockDto topicBlockDto) {
        List<TopicDto> categories = topicBlockDto.getCategories().stream()
                .map(this::mapTopicItemDtoToTopicDto)
                .collect(Collectors.toList());
        return TopicDto.builder()
                .name(topicBlockDto.getName())
                .categories(categories)
                .build();
    }

    TopicBlock Depracated_toTopicBlock(TopicDto topic) {
        return TopicBlock.builder()
                .name(topic.getName())
                .build();
    }

    TopicBlock toTopicBlock(Topic topic) {
        return TopicBlock.builder()
                .name(topic.getName())
                .build();
    }

    Page<TopicBlock> Depracated_mapToTopicBlockPage(Page<TopicDto> topicPage, Long parentId, PageRequest pageRequest) {
        return new PageImpl<>(
                topicPage.getContent().stream()
                        .map(topicDto ->
                                TopicBlock.builder()
                                        .parentId(parentId)
                                        .build()
                        )
                        .collect(Collectors.toList()),
                pageRequest, topicPage.getTotalElements()
        );
    }

    Page<TopicBlock> Depracated_toTopicBlockPage(Page<Topic> topicPage, Long parentId, PageRequest pageRequest) {
        return new PageImpl<>(
                topicPage.getContent().stream()
                        .map(this::toTopicBlock)
                        .peek(tb -> tb.setParentId(parentId))
                        .collect(Collectors.toList()),
                pageRequest, topicPage.getTotalElements()
        );
    }

    Page<TopicBlockDto> toBlockPageDto(Page<Topic> topicPage, Long parentId, PageRequest pageRequest) {
        return new PageImpl<>(
                topicPage.getContent().stream()
                        .map(this::toBlockDto)
                        .peek(tb -> tb.setParentId(parentId))
                        .collect(Collectors.toList()),
                pageRequest, topicPage.getTotalElements()
        );
    }

    TopicBlockDto toBlockDto(Topic topic) {
        return TopicBlockDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .isCategory(topic.isCategory())
                .build();
    }

    TopicDto mapTopicItemDtoToTopicDto(TopicItemDto topicItemDto) {
        return TopicDto.builder()
                .id(topicItemDto.getValue())
                .name(topicItemDto.getLabel())
                .build();
    }

    TopicItemDto mapTopicDtoToTopicItemDto(TopicDto topicDto) {
        return new TopicItemDto(topicDto.getName(), topicDto.getId());
    }
}
