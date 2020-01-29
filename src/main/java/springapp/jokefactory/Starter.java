package springapp.jokefactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.repository.JokeRepository;
import springapp.jokefactory.domain.repository.StructureRepository;
import springapp.jokefactory.services.StructureService;

@Component
@Scope("singleton")
public class Starter implements CommandLineRunner {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    StructureService structureService;


    @Override
    public void run(String... args) throws Exception {
        jokeRepository.createJoke("joke1", "content1");
        jokeRepository.createJoke("joke2", "content2");

        structureRepository.createStrcture("Structure1", "Description1");
        structureRepository.createStrcture("Structure2", "Description2");
    }
}
