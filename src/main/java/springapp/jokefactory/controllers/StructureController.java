package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.services.StructureService;

import javax.validation.Valid;
import java.util.List;

@Controller 
public class StructureController {

    @Autowired
    StructureService structureService;

    @RequestMapping("/structures")
    public String getJokes(Model model){
        List<Structure> allStructures = structureService.getAllStructures();
        model.addAttribute("structures", allStructures);
        return "structures";
    }

    @RequestMapping("/newstructure")
    public String createStructure(Model model){
        model.addAttribute("structure", new Structure());
        return "structurecreation";
    }

    @RequestMapping(value = "/structures", method = RequestMethod.POST)
    public String saveStructure(@Valid Structure structure, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("There were errors");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "jokeform";
        } else {
            structureService.saveStructure(structure);
            return "redirect:/structures";
        }
    }
}
