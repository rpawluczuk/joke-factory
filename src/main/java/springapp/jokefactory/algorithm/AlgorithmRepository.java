package springapp.jokefactory.algorithm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    @Query(value = "SELECT max(id) FROM Algorithm")
    long findHighestID();

    @Query(value = "SELECT j.algorithms FROM Joke j where j.id = ?1")
    List<Algorithm> findAlgorithmsByJokeID(long jokeId);

    Algorithm findFirstByName(String name);

    Page<Algorithm> findStructureByNameContaining(@RequestParam("name") String name, Pageable pageable);
}
