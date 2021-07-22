package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springapp.jokefactory.entity.Structure;

import java.util.List;

public interface StructureRepository extends JpaRepository<Structure, Long> {

    @Query(value = "SELECT max(id) FROM Structure")
    long findHighestID();

    @Query(value = "SELECT j.structures FROM Joke j where j.id = ?1")
    List<Structure> findStructuresByJokeID(long jokeId);
}
