package springapp.jokefactory.testutil.factory;

import springapp.jokefactory.topic.panel.TopicBlockDto;

import java.util.function.Consumer;

public class TopicBlockDtoFactory {

    public static TopicBlockDto createWithoutParent() {
        return createWithoutParent(t -> {});
    }

    public static TopicBlockDto createWithoutParent(Consumer<TopicBlockDto> overrides) {
        TopicBlockDto dto = new TopicBlockDto();
        dto.setId(1L);
        dto.setName("Parent Topic");
        dto.setParentId(null);
        dto.setTopicPackIndex(1);
        overrides.accept(dto);
        return dto;
    }

    public static TopicBlockDto createWithParent() {
        return createWithParent(t -> {});
    }

    public static TopicBlockDto createWithParent(Consumer<TopicBlockDto> overrides) {
        TopicBlockDto dto = new TopicBlockDto();
        dto.setId(2L);
        dto.setName("Child Topic 1");
        dto.setParentId(1L);
        dto.setTopicPackIndex(1);
        overrides.accept(dto);
        return dto;
    }
}
