package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import springapp.jokefactory.entity.Structure;

@CrossOrigin("http://localhost:4200")
public interface StructureRepository extends JpaRepository<Structure, Long> {

}
