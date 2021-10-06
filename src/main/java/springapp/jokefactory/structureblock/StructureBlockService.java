package springapp.jokefactory.structureblock;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.structureblock.dto.StructureBlockCreatorDto;
import springapp.jokefactory.structureblock.dto.StructureBlockPresenterDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
class StructureBlockService {

    @Autowired
    private StructureBlockRepository structureBlockRepository;

    @Autowired
    private StructureBlockMapper structureBlockMapper;

    Iterable<StructureBlockCreatorDto> getStructureBlockCreatorList(Long structureId) {
        List<StructureBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
        return structureBlockList.stream()
                .map(structureBlockMapper::mapStructureBlockToStructureBlockCreatorDto)
                .collect(Collectors.toList());
    }

    Iterable<StructureBlockPresenterDto> getStructureBlockPresenterList(Long structureId){
        List<StructureBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
        return structureBlockList.stream()
                .map(structureBlockMapper::mapStructureBlockToStructureBlockPresenterDto)
                .collect(Collectors.toList());
    }
}
