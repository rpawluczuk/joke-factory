package springapp.jokefactory.origin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface OriginRepository extends JpaRepository<Origin, Long> {

    @Query(value = "SELECT o FROM Origin o where o.name = :name")
    Optional<Origin> findOriginByName(@Param("name") String name);

    @Query(value = "SELECT o FROM Origin o " +
            "LEFT JOIN o.parents parents " +
            "LEFT JOIN o.children children " +
            "WHERE parents.originParent = :origin OR children.originChild = :origin")
    Set<Origin> findAllConnectedOrigins(@Param("origin") Origin origin);
}
