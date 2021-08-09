package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.entity.Origin;
import springapp.jokefactory.entity.OriginRelation;

public interface OriginRelationRepository extends JpaRepository<OriginRelation, Long> {
}
