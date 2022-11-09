package springapp.jokefactory.topic.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import springapp.jokefactory.topic.Topic;

@Data
public class TopicPackPaginationDto {

    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;

    public TopicPackPaginationDto() {
        this.currentPage = 0;
        this.totalItems = 0;
        this.totalPages = 0;
        this.pageSize = 20;
    }

    public TopicPackPaginationDto(int currentPage, Page<Topic> topicPage) {
        this.currentPage = currentPage;
        this.totalItems = topicPage.getTotalElements();
        this.totalPages = topicPage.getTotalPages();
        this.pageSize = 20;
    }
}
