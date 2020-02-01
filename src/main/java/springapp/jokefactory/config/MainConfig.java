package springapp.jokefactory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springapp.jokefactory.domain.repository.DBJokeRepository;
import springapp.jokefactory.domain.repository.InMemoryJokeRepository;
import springapp.jokefactory.domain.repository.JokeRepository;

@Configuration
public class MainConfig {

    @Bean(name = "inMemoryJokeRepository")
    @Profile("dev")
    public JokeRepository createInMemoryRepo() {
        JokeRepository repo = new InMemoryJokeRepository();
        return repo;
    }

    @Bean(name = "DBJokeRepository")
    @Profile("prod")
    public JokeRepository createDBRepo() {
        JokeRepository repo = new DBJokeRepository();
        return repo;
    }
}
