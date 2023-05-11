package springapp.jokefactory.algorithm.jokediagram;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springapp.jokefactory.algorithm.Algorithm;

import java.util.List;
import java.util.Optional;

interface JokeDiagramRepository extends JpaRepository<JokeBlock, Long> {

    @Query(value = "SELECT jb FROM joke_block jb where jb.joke.id = ?1 order by jb.diagramBlock.position")
    List<JokeBlock> findBlocksByJoke(long jokeId);

//    Optional<List<JokeBlock>> findJokeBlocksByStructureBlock_Structure(Algorithm structure);
}
