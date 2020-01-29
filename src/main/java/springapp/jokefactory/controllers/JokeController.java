package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.services.JokeService;
import springapp.jokefactory.services.StructureService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class JokeController {

    @Autowired
    JokeService jokeService;

    @Autowired
    StructureService structureService;

    @RequestMapping("/jokes")
    public String getJokes(Model model){
        List<Joke> allJokes = jokeService.getAllJokes();
        model.addAttribute("jokes", allJokes);
        List<Structure> allStructures = structureService.getAllStructures();
        model.addAttribute("structures", allStructures);
        return "jokes";
    }

    @RequestMapping("/joke")
    public String getJoke(@RequestParam("id") Integer id, Model model){
        Joke joke = jokeService.getJoke(id);
        model.addAttribute("joke", joke);
        return "joke";
    }

    @RequestMapping("/newjoke")
    public String createJoke(Model model){
        model.addAttribute("joke", new Joke());
        List<Structure> allStructures = structureService.getAllStructures();
        model.addAttribute("structures", allStructures);
        return "jokeform";
    }

    @RequestMapping(value = "/jokes", method = RequestMethod.POST)
    public String saveJoke(@Valid Joke joke, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("There were errors");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "jokeform";
        } else {
            jokeService.saveJoke(joke);
            return "redirect:/jokes";
        }
    }

    @RequestMapping(value = "/joke/delete/{id}")
    public String deleteJoke(@PathVariable("id") Integer id){
        Joke jokeToDelete = jokeService.getJoke(id);
        jokeService.deleteJoke(jokeToDelete);
        return "redirect:/jokes";
    }

    @RequestMapping(value = "/joke/edit/{id}")
    public String editStructures(@PathVariable("id") Integer id, Model model){
        Joke jokeToEdit = jokeService.getJoke(id);
        model.addAttribute("joke", jokeToEdit);
        List<Structure> allStructures = structureService.getAllStructures();
        model.addAttribute("structures", allStructures);
        return "jokeedit";
    }
}
