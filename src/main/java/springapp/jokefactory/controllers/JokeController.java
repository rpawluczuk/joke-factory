package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.domain.repository.JokeRepository;
import springapp.jokefactory.domain.repository.StructureRepository;

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
        List<Joke> allJokes = (List<Joke>) jokeRepository.findAll();
        model.addAttribute("jokes", allJokes);
        List<Structure> allStructures = (List<Structure>) structureRepostory.findAll();
        model.addAttribute("structures", allStructures);
        return "jokes";
    }

//    @RequestMapping("/joke")
//    public String getJoke(@RequestParam("id") Integer id, Model model){
//        Joke joke = jokeService.getJoke(id);
//        model.addAttribute("joke", joke);
//        return "joke";
//    }
//
//    @RequestMapping("/newjoke")
//    public String createJoke(Model model){
//        model.addAttribute("joke", new Joke());
//        List<Structure> allStructures = structureService.getAllStructures();
//        model.addAttribute("structures", allStructures);
//        return "jokeform";
//    }
//
//    @RequestMapping(value = "/jokes", method = RequestMethod.POST)
//    public String saveJoke(@Valid Joke joke, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            System.out.println("There were errors");
//            bindingResult.getAllErrors().forEach(error -> {
//                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
//            });
//            return "jokeform";
//        } else {
//            jokeService.saveJoke(joke);
//            return "redirect:/jokes";
//        }
//    }
//
//    @RequestMapping(value = "/joke/delete/{id}")
//    public String deleteJoke(@PathVariable("id") Integer id){
//        Joke jokeToDelete = jokeService.getJoke(id);
//        jokeService.deleteJoke(jokeToDelete);
//        return "redirect:/jokes";
//    }
//
//    @RequestMapping(value = "/joke/edit/{id}")
//    public String editStructures(@PathVariable("id") Integer id, Model model){
//        Joke jokeToEdit = jokeService.getJoke(id);
//        model.addAttribute("joke", jokeToEdit);
//        List<Structure> allStructures = structureService.getAllStructures();
//        model.addAttribute("structures", allStructures);
//        return "jokeedit";
//    }
}
