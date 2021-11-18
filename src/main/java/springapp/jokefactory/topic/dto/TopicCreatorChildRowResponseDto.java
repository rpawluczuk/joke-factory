package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicCreatorChildRowResponseDto {

    private List<TopicCreatorChildDto> topicCreatorChildList;
    private Long parentId;
    private TopicPaginationDto topicPagination;
}
