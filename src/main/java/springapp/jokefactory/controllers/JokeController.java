package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.services.JokeService;

import java.util.List;

@Controller
public class JokeController {

    @Autowired
    JokeService service;

    @RequestMapping("/jokes")
    public String getJokes(Model model){
        List<Joke> allJokes = service.getAllJokes();
        model.addAttribute("jokes", allJokes);
        return "jokes";
    }

    @RequestMapping("/joke")
    public String getJoke(@RequestParam("id") Integer id, Model model){
        Joke joke = service.getJoke(id);
        model.addAttribute("joke", joke);
        return "joke";
    }

    @RequestMapping("/newjoke")
    public String createJoke(Model model){
        model.addAttribute("joke", new Joke());
        return "jokeform";
    }

    @RequestMapping(value = "/jokes", method = RequestMethod.POST)
    public String saveJoke(Joke joke){
        service.saveJoke(joke);
        return "redirect:/jokes";
    }
}
