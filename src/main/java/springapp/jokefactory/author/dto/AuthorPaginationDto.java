package springapp.jokefactory.author.dto;

import lombok.Data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("singleton")
public class AuthorPaginationDto {

    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;

    public AuthorPaginationDto() {
        this.currentPage = 0;
        this.totalItems = 0;
        this.totalPages = 0;
        this.pageSize = 5;
    }

    public AuthorPaginationDto(int currentPage, long totalItems) {
        this.currentPage = currentPage;
        this.totalItems = totalItems;
    }
}
