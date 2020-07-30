package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springapp.jokefactory.entity.Structure;

public interface StructureRepository extends JpaRepository<Structure, Long> {

}
