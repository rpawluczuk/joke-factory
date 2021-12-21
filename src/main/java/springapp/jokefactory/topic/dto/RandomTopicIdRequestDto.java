package springapp.jokefactory.topic.dto;

import lombok.Data;

@Data
public class RandomTopicIdRequestDto {

    private Long parentId;
    private int totalPages;
    private int pageSize;
}
