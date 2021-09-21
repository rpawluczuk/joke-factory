package springapp.jokefactory.origin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface OriginRelationRepository extends JpaRepository<OriginRelation, OriginRelationKey> {

    Optional<List<OriginRelation>> findOriginRelationsByOriginParent(Origin origin);
    Optional<List<OriginRelation>> findOriginRelationsByOriginChild(Origin origin);

    @Query(value = "SELECT orel FROM OriginRelation orel " +
            "WHERE orel.originChild.id = :originId OR orel.originParent.id = :originId")
    List<OriginRelation> findAllOriginRelationsConnectedWithOrigin(@Param("originId") Long originId);
}
