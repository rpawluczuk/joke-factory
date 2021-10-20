package springapp.jokefactory.categorization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface CategorizationRepository extends JpaRepository<Categorization, Long> {
}
