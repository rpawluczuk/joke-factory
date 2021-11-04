package springapp.jokefactory.structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

interface StructureRepository extends JpaRepository<Structure, Long> {

    @Query(value = "SELECT max(id) FROM Structure")
    long findHighestID();

    @Query(value = "SELECT j.structures FROM Joke j where j.id = ?1")
    List<Structure> findStructuresByJokeID(long jokeId);

    Structure findFirstByName(String name);

    Page<Structure> findStructureByNameContaining(@RequestParam("name") String name, Pageable pageable);
}
