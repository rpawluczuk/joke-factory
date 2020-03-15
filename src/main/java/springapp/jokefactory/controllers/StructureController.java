package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.domain.repository.StructureRepository;

import java.util.List;

@Controller
public class StructureController {

    @Autowired
    StructureRepository structureRepository;

    @RequestMapping("/structures")
    public String getJokes(Model model){
        List<Structure> allStructures = (List<Structure>) structureRepository.findAll();
        model.addAttribute("structures", allStructures);
        return "structures";
    }
//
//    @RequestMapping("/newstructure")
//    public String createStructure(Model model){
//        model.addAttribute("structure", new Structure());
//        return "structurecreation";
//    }
//
//    @RequestMapping(value = "/structures", method = RequestMethod.POST)
//    public String saveStructure(@Valid Structure structure, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            System.out.println("There were errors");
//            bindingResult.getAllErrors().forEach(error -> {
//                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
//            });
//            return "structurecreation";
//        } else {
//            structureService.saveStructure(structure);
//            return "redirect:/structures";
//        }
//    }
//
//    @RequestMapping(value = "/structure/delete/{id}")
//    public String deleteStructures(@PathVariable("id") Integer id){
//        Structure structureToDelete = structureService.getStructure(id);
//        structureService.deleteStructure(structureToDelete);
//        return "redirect:/structures";
//    }
//
//    @RequestMapping(value = "/structure/edit/{id}")
//    public String editStructures(@PathVariable("id") Integer id, Model model){
//        Structure structureToEdit = structureService.getStructure(id);
//        model.addAttribute("structure", structureToEdit);
//        return "structureedit";
//    }
}
