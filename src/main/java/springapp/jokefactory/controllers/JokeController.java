package springapp.jokefactory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.services.JokeService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JokeController {

    @Autowired
    JokeService service;

    @RequestMapping("/jokes")
    public String getJoke(Model model){
        List<Joke> allJokes = service.getAllJokes();
        model.addAttribute("jokes", allJokes);
        return "jokes";
    }
}
