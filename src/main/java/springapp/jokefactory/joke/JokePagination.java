package springapp.jokefactory.joke;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("singleton")
class JokePagination {

    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;

    JokePagination() {
        this.currentPage = 0;
        this.totalItems = 0;
        this.totalPages = 0;
        this.pageSize = 5;
    }
}
