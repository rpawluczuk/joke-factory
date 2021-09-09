package springapp.jokefactory.origin;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.origin.OriginRelation;
import springapp.jokefactory.origin.OriginRelationKey;

public interface OriginRelationRepository extends JpaRepository<OriginRelation, OriginRelationKey> {

}
