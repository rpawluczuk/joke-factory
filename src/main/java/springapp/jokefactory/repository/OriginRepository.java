package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springapp.jokefactory.entity.Origin;
import springapp.jokefactory.entity.Structure;

import java.util.List;
import java.util.Optional;

public interface OriginRepository extends JpaRepository<Origin, Long> {

    @Query(value = "SELECT o FROM Origin o where o.name = :name")
    Optional<Origin> findOriginByName(@Param("name") String name);
}
