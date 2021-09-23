package springapp.jokefactory.origin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface OriginRelationRepository extends JpaRepository<OriginRelation, OriginRelationKey> {

    @Query(value = "SELECT orel FROM OriginRelation orel " +
            "WHERE orel.originChild.id = :originId OR orel.originParent.id = :originId")
    List<OriginRelation> findAllOriginRelationsConnectedWithOrigin(@Param("originId") Long originId);
}
