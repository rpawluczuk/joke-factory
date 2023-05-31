package springapp.jokefactory.joke.jokeblock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface JokeBlockRepository extends JpaRepository<JokeBlock, Long> {

    @Query(value = "SELECT jb FROM joke_block jb where jb.joke.id = ?1 order by jb.algorithmBlock.position")
    List<JokeBlock> findBlocksByJoke(long jokeId);

    @Query(value = "SELECT jb FROM joke_block jb where jb.algorithmBlock.algorithm.id = ?1")
    List<JokeBlock> findJokeBlocksByAlgorithmId(long algorithmId);
}
