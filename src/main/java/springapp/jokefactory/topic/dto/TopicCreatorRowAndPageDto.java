package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicCreatorRowAndPageDto {

    private List<TopicItemDto> topicItemList;
    private long totalItems;
    private int totalPages;
}
