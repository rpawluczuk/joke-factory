package springapp.jokefactory.topic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicPageDto {


    private List<TopicDto> content;
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
}
