package springapp.jokefactory.structureblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.algorithm.Algorithm;
import springapp.jokefactory.structureblock.dto.StructureBlockCreatorDto;
import springapp.jokefactory.structureblock.dto.StructureBlockPresenterDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StructureBlockFacade {

    @Autowired
    private StructureBlockRepository structureBlockRepository;

    @Autowired
    private StructureBlockMapper structureBlockMapper;

    public StructureBlock tryToGetStructureBlockByID(Long id) {
        return structureBlockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No structure block found with id: " + id));
    }

    public List<StructureBlock> getStructureBlocksByStructure(long structureId) {
        return structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
    }

    public List<StructureBlock> getStructureBlocksByJoke(long jokeId) {
        return structureBlockRepository.findStructureBlocksByJoke(jokeId);
    }

    public List<StructureBlock> extractStructureBlockList(List<StructureBlockCreatorDto> structureBlockCreatorList,
                                                          Algorithm structure) {
        return structureBlockCreatorList.stream()
                .map(structureBlockCreatorDto ->
                        structureBlockMapper.mapStructureBlockCreatorDtoToStructureBlock(structureBlockCreatorDto, structure))
                .collect(Collectors.toList());
    }

    public List<StructureBlock> extractUpdatedStructureBlockList(List<StructureBlockCreatorDto> structureBlockCreatorDtoList,
                                                                 List<StructureBlock> structureBlockList,
                                                                 Algorithm structure) {
        return IntStream.range(0, structureBlockCreatorDtoList.size())
                .mapToObj(index -> {
                    if (index < structureBlockList.size()){
                        return structureBlockMapper.updateStructureBlock(structureBlockList.get(index), structureBlockCreatorDtoList.get(index));
                    }
                    return structureBlockMapper.mapStructureBlockCreatorDtoToStructureBlock(structureBlockCreatorDtoList.get(index), structure);
                })
                .collect(Collectors.toList());
    }

    public StructureBlockPresenterDto extractStructureBlockPresenterDto(StructureBlock structureBlock) {
        return structureBlockMapper.mapStructureBlockToStructureBlockPresenterDto(structureBlock);
    }
}
