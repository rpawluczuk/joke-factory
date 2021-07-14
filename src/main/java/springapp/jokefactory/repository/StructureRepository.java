package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springapp.jokefactory.entity.Structure;

public interface StructureRepository extends JpaRepository<Structure, Long> {

    @Query(value = "SELECT max(id) FROM Structure")
    public long findHighestID();
}
