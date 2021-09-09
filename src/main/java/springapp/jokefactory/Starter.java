//package springapp.jokefactory;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import springapp.jokefactory.joke.Joke;
//import springapp.jokefactory.structure.Structure;
//import springapp.jokefactory.joke.JokeRepository;
//import springapp.jokefactory.structure.StructureRepository;
//
//import javax.transaction.Transactional;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//@Component
//@Scope("singleton")
//public class Starter implements CommandLineRunner {
//
//    @Autowired
//    JokeRepository jokeRepository;
//
//    @Autowired
//    StructureRepository structureRepository;
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        Joke joke = new Joke();
//        joke.setId(999L);
//        joke.setTitle("many_many");
//        joke.setContent("many_many");
//        Set<Structure> structures = new HashSet<>();
//        Structure structure1 = structureRepository.findById(1L).get();
//        Structure structure2 = structureRepository.findById(2L).get();
//        Structure structure3 = new Structure();
//        structures.add(structure3);
//        structures.add(structure1);
//        structures.add(structure2);
//        joke.setStructures(structures);
//        jokeRepository.save(joke);
//        Optional<Joke> newJoke = jokeRepository.findById(999L);
//    }
//}
