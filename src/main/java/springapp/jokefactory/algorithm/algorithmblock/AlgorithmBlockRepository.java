package springapp.jokefactory.algorithm.algorithmblock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface AlgorithmBlockRepository extends JpaRepository<AlgorithmBlock, Long> {

    List<AlgorithmBlock> findAlgorithmBlocksByAlgorithm_IdOrderByPosition(long structureId);

    AlgorithmBlock findAlgorithmBlockByAlgorithm_IdAndPosition(Long algorithmId, int position);

    //    @Query(value = "SELECT db FROM diagram_block db JOIN db.algorithm a JOIN a.jokes j WHERE j.id = ?1")
//    List<DiagramBlock> findDiagramBlocksByJoke(long jokeId);
}
