package springapp.jokefactory.structureblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapp.jokefactory.structureblock.dto.StructureBlockCreatorDto;
import springapp.jokefactory.algorithm.diagram.DiagramBlockPresenterDto;

//@RestController
//@RequestMapping("/api/structure-blocks")
//@CrossOrigin("http://localhost:4200")
//class StructureBlockController {
//
//    @Autowired
//    StructureBlockService structureBlockService;
//
//    @GetMapping(value = "/creator-list/{structure_id}")
//    Iterable<StructureBlockCreatorDto> getStructureBlockCreatorList(@PathVariable("structure_id") Long structureId){
//        return structureBlockService.getStructureBlockCreatorList(structureId);
//    }
//
//    @GetMapping(value = "/presenter-list/{structure_id}")
//    Iterable<DiagramBlockPresenterDto> getStructureBlockPresenterList(@PathVariable("structure_id") Long structureId){
//        return structureBlockService.getStructureBlockPresenterList(structureId);
//    }
//}
