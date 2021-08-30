package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springapp.jokefactory.entity.JokeBlock;
import springapp.jokefactory.entity.Structure;

import java.util.List;

public interface JokeBlockRepository extends JpaRepository<JokeBlock, Long> {

    @Query(value = "SELECT jb FROM joke_block jb where jb.joke.id = ?1 order by jb.structureBlock.position")
    List<JokeBlock> findBlocksByJoke(long jokeId);

    List<JokeBlock> findJokeBlocksByStructureBlock_Structure(Structure structure);
}
