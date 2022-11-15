package springapp.jokefactory.topic.panel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicPageDto {

    private List<TopicBlockDto> content;
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
}
