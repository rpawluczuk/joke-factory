package springapp.jokefactory.structure.structureblock;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.structure.StructureRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blocks")
@CrossOrigin("http://localhost:4200")
public class StructureBlockController {

    @Autowired
    StructureBlockRepository structureBlockRepository;

    @Autowired
    StructureRepository structureRepository;

    private final StructureBlockMapper structureBlockMapper = Mappers.getMapper(StructureBlockMapper.class);

    @GetMapping
    public Iterable<StructureBlock> getStructureBlocks(){
        return structureBlockRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<StructureBlock> getStructureBlockById(@PathVariable("id") Long id){
        return structureBlockRepository.findById(id);
    }

    @GetMapping(value = "with-structure/{structure_id}")
    public Iterable<StructureBlock> getBlocksOfTheStructure(@PathVariable("structure_id") Long structureID){
        List<StructureBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureID);
        AtomicInteger position = new AtomicInteger();
        structureBlockList = structureBlockList.stream().map(structureBlock -> {
            if (structureBlock.getPosition() != position.get()) {
                structureBlock.setPosition(position.get());
            }
            position.getAndIncrement();
            return structureBlock;
        }).collect(Collectors.toList());
        return structureBlockList;
    }

    @GetMapping(value = "/presenter-list/{structure_id}")
    public Iterable<StructureBlockPresenterDto> getStructureBlockPresenterList(@PathVariable("structure_id") Long structureId){
        List<StructureBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
        return structureBlockList.stream()
                .map(structureBlockMapper::mapStructureBlockToStructureBlockPresenterDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addStructureBlock(@RequestBody StructureBlock structureBlock){
        structureBlockRepository.save(structureBlock);
    }

    @PutMapping
    public void editStructureBlockList(@RequestBody List<StructureBlock> newStructureBlockList){
        List<StructureBlock> oldStructureBlockList = newStructureBlockList.stream().findFirst()
                .map(structureBlock -> structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureBlock.getStructure().getId()))
                .orElse(Collections.emptyList());
        List<Long> newStructureBlockIdList = newStructureBlockList.stream().map(StructureBlock::getId).collect(Collectors.toList());
        oldStructureBlockList.forEach(oldStructureBlock -> {
            if (!newStructureBlockIdList.contains(oldStructureBlock.getId())) {
                structureBlockRepository.delete(oldStructureBlock);
            }
        });
        newStructureBlockList.forEach(structureBlock -> structureBlockRepository.save(structureBlock));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteStructureBlock(@PathVariable("id") Long id){
        StructureBlock structureBlockToDelete = structureBlockRepository.findById(id).get();
        structureBlockRepository.delete(structureBlockToDelete);
    }
}
