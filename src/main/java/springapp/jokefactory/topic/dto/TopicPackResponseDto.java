package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicPackResponseDto {

    private List<TopicCreatorDto> topicCreatorChildList;
    private Long parentId;
    private TopicPackPaginationDto topicPackPagination;
}
