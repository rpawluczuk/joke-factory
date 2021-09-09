package springapp.jokefactory.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springapp.jokefactory.utils.Pagination;

@RestController
@RequestMapping("/api/pagination")
@CrossOrigin("http://localhost:4200")
public class PaginationController {


    @Autowired
    Pagination pagination;

    @GetMapping
    public Pagination getPagination(){
        return pagination;
    }

    @PutMapping
    public void updatePagination(@RequestBody Pagination pagination){
        this.pagination.setCurrentPage(pagination.getCurrentPage());
        this.pagination.setTotalItems(pagination.getTotalItems());
        this.pagination.setTotalPages(pagination.getTotalPages());
        this.pagination.setPageSize(pagination.getPageSize());
    }
}
