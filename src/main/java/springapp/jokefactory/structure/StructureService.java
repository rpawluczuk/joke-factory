package springapp.jokefactory.structure;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.structure.dto.StructureCreatorDto;
import springapp.jokefactory.structure.dto.StructureItemDto;
import springapp.jokefactory.structure.dto.StructurePresenterDto;
import springapp.jokefactory.structureblock.StructureBlock;
import springapp.jokefactory.structureblock.StructureBlockFacade;

import java.util.List;
import java.util.stream.Collectors;

@Service
class StructureService {

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private StructureFacade structureFacade;

    @Autowired
    private StructureBlockFacade structureBlockFacade;

    @Autowired
    private JokeFacade jokeFacade;

    @Autowired
    private StructureMapper structureMapper;

    StructureCreatorDto getStructureCreatorDto(Long id) {
        Structure structure = structureFacade.tryToGetStructureById(id);
        return structureMapper.mapStructureToStructureCreatorDto(structure);
    }

    Iterable<StructurePresenterDto> getStructurePresenterList() {
        return structureRepository.findAll().stream()
                .map(structureMapper::mapStructureToStructurePresenterDto)
                .collect(Collectors.toList());
    }

    Iterable<StructureItemDto> getStructureItemList() {
        return structureRepository.findAll().stream()
                .map(structureMapper::mapStructureToStructureItemDto)
                .collect(Collectors.toList());
    }

    Iterable<StructureItemDto> getStructureItemListByJokeID(Long jokeID) {
        return structureRepository.findStructuresByJokeID(jokeID).stream()
                .map(structureMapper::mapStructureToStructureItemDto)
                .collect(Collectors.toList());
    }

    void addStructure(StructureCreatorDto structureCreatorDto) {
        Structure structure = structureMapper.mapStructureCreatorDtoToStructure(structureCreatorDto);
        List<StructureBlock> structureBlockList =
                structureBlockFacade.extractStructureBlockList(structureCreatorDto.getStructureBlockCreatorDtoList(), structure);
        structure.setStructureBlockScheme(structureBlockList);
        structureRepository.save(structure);
    }

    void editStructure(StructureCreatorDto structureCreatorDto) {
        Structure structure = structureFacade.tryToGetStructureById(structureCreatorDto.getId());
        Structure updatedStructure = structureMapper.updateStructure(structure, structureCreatorDto);
        List<StructureBlock> structureBlockList = structureBlockFacade.getStructureBlocksByStructure(structure.getId());
        List<StructureBlock> updatedStructureBlockList = structureBlockFacade.extractUpdatedStructureBlockList(
                structureCreatorDto.getStructureBlockCreatorDtoList(), structureBlockList, structure);
        updatedStructure.setStructureBlockScheme(updatedStructureBlockList);
        structureRepository.save(updatedStructure);
    }

    void deleteStructure(Long id) {
        Structure structureToDelete = structureFacade.tryToGetStructureById(id);
        jokeFacade.removeStructureFromJokes(structureToDelete);
        structureRepository.delete(structureToDelete);
    }
}
