package springapp.jokefactory;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.repository.JokeRepository;

import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest
class JokeFactoryApplicationTests {

    @Autowired
    JokeRepository jokeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreate(){
        Joke joke = new Joke();
        joke.setId(1);
        joke.setTitle("żart o gejach");
        joke.setContent("ten tego");
        joke.setAuthor("Adam Mickiewicz");

        jokeRepository.save(joke);
    }

    @Test
    public void testRead(){
        Optional<Joke> joke = jokeRepository.findById(1);
        assertTrue(joke.isPresent());
        assertEquals("żart o gejach", joke.get().getTitle());
    }

    @Test
    public void testUpdate(){
        Optional<Joke> joke = jokeRepository.findById(1);
        assertTrue(joke.isPresent());
        joke.get().setTitle("Najkrótszy żart o gejach");
        jokeRepository.save(joke.get());
    }

    @Test
    public void testDelete(){
        jokeRepository.deleteById(1);
    }
}
