//package springapp.jokefactory.structureblock;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import springapp.jokefactory.algorithm.diagram.DiagramBlock;
//import springapp.jokefactory.algorithm.StructureBlockRepository;
//import springapp.jokefactory.algorithm.diagram.dto.DiagramBlockPresenterDto;
//import springapp.jokefactory.structureblock.dto.StructureBlockCreatorDto;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//class StructureBlockService {
//
//    @Autowired
//    private StructureBlockRepository structureBlockRepository;
//
//    @Autowired
//    private StructureBlockMapper structureBlockMapper;
//
//    Iterable<StructureBlockCreatorDto> getStructureBlockCreatorList(Long structureId) {
//        List<DiagramBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
//        return structureBlockList.stream()
//                .map(structureBlockMapper::mapStructureBlockToStructureBlockCreatorDto)
//                .collect(Collectors.toList());
//    }
//
//    Iterable<DiagramBlockPresenterDto> getStructureBlockPresenterList(Long structureId){
//        List<DiagramBlock> structureBlockList = structureBlockRepository.findStructureBlocksByStructure_IdOrderByPosition(structureId);
//        return structureBlockList.stream()
//                .map(structureBlockMapper::mapStructureBlockToStructureBlockPresenterDto)
//                .collect(Collectors.toList());
//    }
//}
