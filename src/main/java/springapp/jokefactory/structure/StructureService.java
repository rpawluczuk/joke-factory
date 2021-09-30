package springapp.jokefactory.structure;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.joke.JokeRepository;
import springapp.jokefactory.structure.structureblock.StructureBlock;
import springapp.jokefactory.structure.structureblock.StructureBlockMapper;
import springapp.jokefactory.structure.structureblock.StructureBlockRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
class StructureService {

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    StructureBlockRepository structureBlockRepository;

    @Autowired
    JokeRepository jokeRepository;

    private final StructureMapper structureMapper = Mappers.getMapper(StructureMapper.class);
    private final StructureBlockMapper structureBlockMapper = Mappers.getMapper(StructureBlockMapper.class);

    StructureCreatorDto getStructureCreatorDto(Long id){
        Structure structure = structureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + id));
        return structureMapper.mapStructureToStructureCreatorDto(structure);
    }

    Iterable<StructurePresenterDto> getStructurePresenterList(){
        return structureRepository.findAll().stream()
                .map(structureMapper::mapStructureToStructurePresenterDto)
                .collect(Collectors.toList());
    }

    Iterable<StructureItemDto> getStructureItemList() {
        return structureRepository.findAll().stream()
                .map(structureMapper::mapStructureToStructureItemDto)
                .collect(Collectors.toList());
    }

    Iterable<StructureItemDto> getStructureItemListByJokeID(Long jokeID){
        return structureRepository.findStructuresByJokeID(jokeID).stream()
                .map(structureMapper::mapStructureToStructureItemDto)
                .collect(Collectors.toList());
    }

    void addStructure(StructureCreatorDto structureCreatorDto){
        Structure structure = structureMapper.mapStructureCreatorDtoToStructure(structureCreatorDto);
        Set<StructureBlock> structureBlockList = structureCreatorDto.getStructureBlockCreatorDtoList().stream()
                .map(structureBlockCreatorDto ->
                        structureBlockMapper.mapStructureBlockCreatorDtoToStructureBlock(structureBlockCreatorDto, structure))
                .collect(Collectors.toSet());
        structure.setStructureBlockScheme(structureBlockList);
        structureRepository.save(structure);
    }

    void editStructure(StructureCreatorDto structureCreatorDto){
        Structure structure = structureRepository.findById(structureCreatorDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + structureCreatorDto.getId()));
        Structure updatedStructure = structureMapper.updateStructure(structure, structureCreatorDto);
        List<StructureBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structure.getId());
        Set<StructureBlock> updatedStructureBlockList = IntStream.range(0, structureCreatorDto.getStructureBlockCreatorDtoList().size())
                .mapToObj(index -> {
                    if (index < structureBlockList.size()){
                        return structureBlockMapper.updateStructureBlock(structureBlockList.get(index), structureCreatorDto.getStructureBlockCreatorDtoList().get(index));
                    }
                    return structureBlockMapper.mapStructureBlockCreatorDtoToStructureBlock(structureCreatorDto.getStructureBlockCreatorDtoList().get(index), structure);
                })
                .collect(Collectors.toSet());
        updatedStructure.setStructureBlockScheme(updatedStructureBlockList);
        structureRepository.save(updatedStructure);
    }

    void deleteStructure(Long id){
        Structure structureToDelete = structureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + id));
        for (Joke joke : structureToDelete.getJokes()) {
            joke.getStructures().remove(structureToDelete);
            jokeRepository.save(joke);
        }
        structureRepository.delete(structureToDelete);
    }
}
