package springapp.jokefactory.topic.panel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;

import java.util.Optional;

public class TopicPackDtoBuilder {

    private final TopicFacade topicFacade;
    private final TopicPanelMapper topicPanelMapper;

    private Topic topicParent;
    private Integer topicPackIndex;
    private PageRequest pageRequest;
    private Long selectedId;

    public TopicPackDtoBuilder(TopicFacade topicFacade, TopicPanelMapper topicPanelMapper) {
        this.topicFacade = topicFacade;
        this.topicPanelMapper = topicPanelMapper;
    }

    public static TopicPackDtoBuilder builder(TopicFacade topicFacade, TopicPanelMapper topicPanelMapper) {
        return new TopicPackDtoBuilder(topicFacade, topicPanelMapper);
    }

    public TopicPackDtoBuilder withParent(Topic topicParent) {
        this.topicParent = topicParent;
        return this;
    }

    public TopicPackDtoBuilder withTopicPackIndex(Integer topicPackIndex) {
        this.topicPackIndex = topicPackIndex;
        return this;
    }

    public TopicPackDtoBuilder withPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
        return this;
    }

    public TopicPackDtoBuilder withSelectedId(Long selectedId) {
        this.selectedId = selectedId;
        return this;
    }

    public TopicPackDto build() {
        if (topicParent == null) {
            throw new IllegalStateException("topicParent is required!");
        }
        if (pageRequest == null) {
            pageRequest = PageRequest.of(0, 23, Sort.Direction.ASC, "name");
        }

        TopicBlockDto topicBlockParent = topicPanelMapper.toBlockDto(topicParent);
        Page<Topic> topicPage = topicFacade.getConnectedTopicsPage(topicBlockParent.getId(), pageRequest);
        Page<TopicBlockDto> topicBlockPage = topicPanelMapper.toBlockPageDto(
            topicPage,
            topicBlockParent.getId(),
            pageRequest
        );
        Optional<Long> selected = Optional.ofNullable(this.selectedId);
        selected.flatMap(aLong -> topicBlockPage.getContent().stream()
            .filter(topicBlockDto -> topicBlockDto.getId().equals(aLong))
            .findFirst()
        ).ifPresent(topicBlockDto -> topicBlockDto.setSelected(true));

        return TopicPackDto.builder()
            .topicBlockParent(topicBlockParent)
            .topicBlockPage(topicBlockPage)
            .topicPackIndex(topicPackIndex)
            .build();
    }
}
