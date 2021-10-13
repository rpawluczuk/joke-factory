package springapp.jokefactory.categorization;

import org.springframework.data.jpa.repository.JpaRepository;

interface CategorizationRepository extends JpaRepository<Categorization, Long> {
}
