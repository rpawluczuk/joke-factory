package springapp.jokefactory.topic.dto;

import lombok.Data;

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

    public TopicPackPaginationDto(int currentPage, long totalItems) {
        this.currentPage = currentPage;
        this.totalItems = totalItems;
    }
}
