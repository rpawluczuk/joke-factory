package springapp.jokefactory.categorization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

interface CategorizationRepository extends JpaRepository<Categorization, Long> {

    Page<Categorization> findCategorizationByNameContaining(@RequestParam("name") String name, Pageable pageable);
}
