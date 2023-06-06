package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.algorithmblock.AlgorithmBlockFacade;
import springapp.jokefactory.algorithm.algorithmblock.dto.AlgorithmBlockDto;
import springapp.jokefactory.algorithm.dto.AlgorithmDto;
import springapp.jokefactory.algorithm.dto.AlgorithmItemDto;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
class AlgorithmMapper {

    @Autowired
    AlgorithmBlockFacade diagramFacade;

    AlgorithmDto mapAlgorithmToDto(Algorithm algorithm) {
        List<AlgorithmBlockDto> diagramBlockList = diagramFacade.getAlgorithmBlockList(algorithm.getId());
        String dateCreated = new SimpleDateFormat("yyyy-MM-dd").format(algorithm.getDateCreated());
        return AlgorithmDto.builder()
                .id(algorithm.getId())
                .name(algorithm.getName())
                .description(algorithm.getDescription())
                .diagramBlockList(diagramBlockList)
                .dateCreated(dateCreated)
                .build();
    }

    Algorithm mapDtoToAlgorithm(AlgorithmDto algorithmDto) {
        return Algorithm.builder()
                .name(algorithmDto.getName())
                .description(algorithmDto.getDescription())
                .build();
    }

    public Algorithm updateAlgorithm(Algorithm algorithm, AlgorithmDto algorithmDto) {
        algorithm.setName(algorithmDto.getName());
        algorithm.setDescription(algorithmDto.getDescription());
        return algorithm;
    }
    public AlgorithmItemDto mapToAlgorithmItemDto(Algorithm algorithm) {
        return new AlgorithmItemDto(algorithm.getName(), algorithm.getId());
    }
}
