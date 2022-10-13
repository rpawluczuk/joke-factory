package springapp.jokefactory.topic.dto;

import lombok.Data;

@Data
public class TopicCreatorChildRowRequestDto {

    private Long parentId;
    private TopicPackPaginationDto topicPackPagination;
}
