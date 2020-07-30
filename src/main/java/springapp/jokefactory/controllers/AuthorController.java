//package springapp.jokefactory.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import springapp.jokefactory.entity.Author;
//import springapp.jokefactory.entity.Joke;
//import springapp.jokefactory.repository.AuthorRepository;
//import springapp.jokefactory.repository.JokeRepository;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//public class AuthorController {
//
//    @Autowired
//    AuthorRepository authorRepository;
//
//    @Autowired
//    JokeRepository jokeRepository;
//
//    @RequestMapping("/authors")
//    public String getAuthors(Model model) {
//        List<Author> allAuthors= authorRepository.findAll();
//        model.addAttribute("authors", allAuthors);
//        return "authors";
//    }
//
//    @RequestMapping("/newauthor")
//    public String createAuthor(Model model) {
//        model.addAttribute("author", new Author());
//        return "authorcreation";
//    }
//
//    @RequestMapping(value = "/authors", method = RequestMethod.POST)
//    public String saveAuthor(@Valid Author author, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("There were errors");
//            bindingResult.getAllErrors().forEach(error -> {
//                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
//            });
//            return "authorcreation";
//        } else {
//            authorRepository.save(author);
//            return "redirect:/authors";
//        }
//    }
//
//    @RequestMapping(value = "/author/delete/{id}")
//    public String deleteAuthor(@PathVariable("id") Long id) {
//        Author authorToDelete = authorRepository.findById(id).get();
//        List<Joke> connectedJokes = jokeRepository.findAll();
//        connectedJokes = connectedJokes.stream()
//                .filter(joke -> joke.getAuthor().getId().equals(id))
//                .collect(Collectors.toList());
//        for (Joke joke : connectedJokes) {
//            joke.setAuthor(null);
//            jokeRepository.save(joke);
//        }
//        authorRepository.delete(authorToDelete);
//        return "redirect:/authors";
//    }
//
//    @RequestMapping(value = "/author/edit/{id}")
//    public String editStructures(@PathVariable("id") Long id, Model model) {
//        Author authorToEdit = authorRepository.findById(id).get();
//        model.addAttribute("author", authorToEdit);
//        return "authoredit";
//    }
//}
