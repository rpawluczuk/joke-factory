package springapp.jokefactory.origin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OriginRelationRepository extends JpaRepository<OriginRelation, OriginRelationKey> {

    Optional<List<OriginRelation>> findOriginRelationsByOriginParent(Origin origin);
    Optional<List<OriginRelation>> findOriginRelationsByOriginChild(Origin origin);
}
