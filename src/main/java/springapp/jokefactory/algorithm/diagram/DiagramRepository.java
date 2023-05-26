package springapp.jokefactory.algorithm.diagram;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface DiagramRepository extends JpaRepository<DiagramBlock, Long> {

    List<DiagramBlock> findDiagramBlocksByAlgorithm_IdOrderByPosition(long structureId);

    DiagramBlock findDiagramBlockByAlgorithm_IdAndPosition(Long algorithmId, int position);

    //    @Query(value = "SELECT db FROM diagram_block db JOIN db.algorithm a JOIN a.jokes j WHERE j.id = ?1")
//    List<DiagramBlock> findDiagramBlocksByJoke(long jokeId);
}
