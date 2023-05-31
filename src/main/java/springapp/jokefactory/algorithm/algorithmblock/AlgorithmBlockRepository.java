package springapp.jokefactory.algorithm.algorithmblock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface AlgorithmBlockRepository extends JpaRepository<AlgorithmBlock, Long> {

    List<AlgorithmBlock> findAlgorithmBlocksByAlgorithm_IdOrderByPosition(long algorithmId);

    AlgorithmBlock findAlgorithmBlockByAlgorithm_IdAndPosition(Long algorithmId, int position);

}
