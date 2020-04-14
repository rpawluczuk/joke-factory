package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springapp.jokefactory.entity.Joke;
import springapp.jokefactory.entity.Structure;
import springapp.jokefactory.repository.JokeRepository;
import springapp.jokefactory.repository.StructureRepository;

import javax.validation.Valid;
import java.util.List;

//import javax.validation.Valid;

@Controller
public class JokeController {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepostory;

    @RequestMapping("/jokes")
    public String getJokes(Model model){
        List<Joke> allJokes = jokeRepository.findAll();
        model.addAttribute("jokes", allJokes);
        List<Structure> allStructures = structureRepostory.findAll();
        model.addAttribute("structures", allStructures);
        return "jokes";
    }

    @RequestMapping("/joke")
    public String getJoke(@RequestParam("id") Long id, Model model){
        Joke joke = jokeRepository.findById(id).get();
        model.addAttribute("joke", joke);
        return "joke";
    }
//
    @RequestMapping("/newjoke")
    public String createJoke(Model model){
        model.addAttribute("joke", new Joke());
        List<Structure> allStructures = structureRepostory.findAll();
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
            jokeRepository.save(joke);
            return "redirect:/jokes";
        }
    }

    @RequestMapping(value = "/joke/delete/{id}")
    public String deleteJoke(@PathVariable("id") Long id){
        Joke jokeToDelete = jokeRepository.findById(id).get();
        jokeRepository.delete(jokeToDelete);
        return "redirect:/jokes";
    }

    @RequestMapping(value = "/joke/edit/{id}")
    public String editStructures(@PathVariable("id") Long id, Model model){
        Joke jokeToEdit = jokeRepository.findById(id).get();
        model.addAttribute("joke", jokeToEdit);
        List<Structure> allStructures = structureRepostory.findAll();
        model.addAttribute("structures", allStructures);
        return "jokeedit";
    }
}
