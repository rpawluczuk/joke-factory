package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.entity.OriginRelation;
import springapp.jokefactory.entity.OriginRelationKey;

public interface OriginRelationRepository extends JpaRepository<OriginRelation, OriginRelationKey> {

}
