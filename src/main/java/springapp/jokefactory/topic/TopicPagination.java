package springapp.jokefactory.topic;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("singleton")
class TopicPagination {

    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;

    TopicPagination() {
        this.currentPage = 0;
        this.totalItems = 0;
        this.totalPages = 0;
        this.pageSize = 10;
    }
}
