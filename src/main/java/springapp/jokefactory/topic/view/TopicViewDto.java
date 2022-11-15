package springapp.jokefactory.topic.view;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicViewDto {

    private List<TopicPresenterDto> content;
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean categoryFilter;
}
