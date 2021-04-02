package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.entity.Origin;

public interface OriginRepository extends JpaRepository<Origin, Long> {
}
