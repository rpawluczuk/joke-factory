package springapp.jokefactory.algorithm.algorithmblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.algorithm.algorithmblock.dto.AlgorithmBlockDto;
import springapp.jokefactory.joke.jokeblock.JokeBlock;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlgorithmBlockFacade {

    @Autowired
    private AlgorithmBlockRepository algorithmBlockRepository;

    @Autowired
    private AlgorithmBlockMapper algorithmBlockMapper;

    public List<AlgorithmBlockDto> getAlgorithmBlockList(long algorithmId) {
        return algorithmBlockRepository.findAlgorithmBlocksByAlgorithm_IdOrderByPosition(algorithmId).stream()
                .map(algorithmBlockMapper::mapAlgorithmBlockToPresenterDto)
                .collect(Collectors.toList());
    }

    public void saveAlgorithmBlockList(List<AlgorithmBlockDto> algorithmBlockDtoList, Algorithm algorithm) {
        algorithmBlockDtoList.stream()
                .map(algorithmBlockDto ->
                        algorithmBlockMapper.mapDtoToAlgorithmBlock(algorithmBlockDto))
                .forEach(algorithmBlock -> {
                    algorithmBlock.setAlgorithm(algorithm);
                    algorithmBlockRepository.save(algorithmBlock);
                });
    }

    public void updateAlgorithmList(List<AlgorithmBlockDto> algorithmBlockDtoList, Algorithm algorithm) {
        List<AlgorithmBlock> algorithmBlockList = algorithmBlockRepository.findAlgorithmBlocksByAlgorithm_IdOrderByPosition(algorithm.getId());
        algorithmBlockList.forEach(algorithmBlock -> {
            boolean exists = algorithmBlockDtoList.stream()
                    .anyMatch(algorithmBlockDto -> algorithmBlockDto.getId().equals(algorithmBlock.getId()));
            if (!exists) {
                algorithmBlockRepository.delete(algorithmBlock);
            }
        });
        algorithmBlockDtoList.stream()
                .map(algorithmBlockDto -> {
                    if (algorithmBlockDto.getId() != null){
                        AlgorithmBlock algorithmBlock = getAlgorithmBlockById(algorithmBlockDto.getId());
                        return algorithmBlockMapper.updateAlgorithmBlock(algorithmBlock, algorithmBlockDto);
                    }
                    return algorithmBlockMapper.mapDtoToAlgorithmBlock(algorithmBlockDto);
                })
                .forEach(algorithmBlock -> {
                    algorithmBlock.setAlgorithm(algorithm);
                    algorithmBlockRepository.save(algorithmBlock);
                });
    }

    public AlgorithmBlock getAlgorithmBlockById(Long id) {
        return algorithmBlockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No algorithm block found with id: " + id));
    }

    public AlgorithmBlock getAlgorithmBlock(Long algorithmId, int position) {
        return algorithmBlockRepository.findAlgorithmBlockByAlgorithm_IdAndPosition(algorithmId, position);
    }

    public void deleteAllAlgorithmBlockByAlgorithmId(long algorithmId) {
        List<AlgorithmBlock> algorithmBlockListToDelete = algorithmBlockRepository.findAlgorithmBlocksByAlgorithm_IdOrderByPosition(algorithmId);
        algorithmBlockRepository.deleteAll(algorithmBlockListToDelete);
    }
}
