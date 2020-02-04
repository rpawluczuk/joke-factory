package springapp.jokefactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Role;
import springapp.jokefactory.domain.User;
import springapp.jokefactory.domain.repository.JokeRepository;
import springapp.jokefactory.domain.repository.RoleRepository;
import springapp.jokefactory.domain.repository.StructureRepository;
import springapp.jokefactory.domain.repository.UserRepository;
import springapp.jokefactory.services.StructureService;

import javax.transaction.Transactional;

@Component
@Scope("singleton")
public class Starter implements CommandLineRunner {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    StructureService structureService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        jokeRepository.createJoke("joke1", "content1");
        jokeRepository.createJoke("joke2", "content2");

        structureRepository.createStrcture("Structure1", "Description1");
        structureRepository.createStrcture("Structure2", "Description2");

        User user1 = new User("user1", "{noop}user1");
        userRepository.persistUser(user1);
        User user2 = new User("user2", "{noop}user2");
        userRepository.persistUser(user2);

        Role user1RoleUSER = new Role("user1", "USER");
        Role user2RoleUSER = new Role("user2", "USER");
        Role user2RoleADMIN = new Role("user2", "ADMIN");

        roleRepository.persistRole(user1RoleUSER);
        roleRepository.persistRole(user2RoleUSER);
        roleRepository.persistRole(user2RoleADMIN);
    }
}
