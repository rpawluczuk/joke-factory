package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StructureController {

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    JokeRepository jokeRepository;

    @RequestMapping("/structures")
    public String getStructures(Model model) {
        List<Structure> allStructures = structureRepository.findAll();
        model.addAttribute("structures", allStructures);
        return "structures";
    }

    @RequestMapping("/newstructure")
    public String createStructure(Model model) {
        model.addAttribute("structure", new Structure());
        return "structurecreation";
    }

    @RequestMapping(value = "/structures", method = RequestMethod.POST)
    public String saveStructure(@Valid Structure structure, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("There were errors");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "structurecreation";
        } else {
            structureRepository.save(structure);
            return "redirect:/structures";
        }
    }

    @RequestMapping(value = "/structure/delete/{id}")
    public String deleteStructure(@PathVariable("id") Long id) {
        Structure structureToDelete = structureRepository.findById(id).get();
        List<Joke> connectedJokes = jokeRepository.findAll();
        connectedJokes = connectedJokes.stream()
                .filter(joke -> joke.getStructure().getId().equals(id))
                .collect(Collectors.toList());
        for (Joke joke : connectedJokes) {
            joke.setStructure(null);
            jokeRepository.save(joke);
        }
        structureRepository.delete(structureToDelete);
        return "redirect:/structures";
    }

    @RequestMapping(value = "/structure/edit/{id}")
    public String editStructures(@PathVariable("id") Long id, Model model) {
        Structure structureToEdit = structureRepository.findById(id).get();
        model.addAttribute("structure", structureToEdit);
        return "structureedit";
    }
}
