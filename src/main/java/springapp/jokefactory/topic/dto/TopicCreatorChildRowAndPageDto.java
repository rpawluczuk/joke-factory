package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicCreatorChildRowAndPageDto {

    private List<TopicCreatorChildDto> topicCreatorChildList;
    private Long parentId;
    private long totalItems;
    private int totalPages;
}
