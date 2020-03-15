package springapp.jokefactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.repository.JokeRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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
        joke.setTitle("żart o gejach");
        joke.setContent("ten tego");
        joke.setAuthor("Adam Mickiewicz");
        jokeRepository.save(joke);
    }

    @Test
    public void testRead(){
        Joke joke = jokeRepository.findById(1).get();
        assertNotNull(joke);
        assertEquals("żart o gejach", joke.getTitle());
    }

    @Test
    public void testUpdate(){
        Joke joke = jokeRepository.findById(1).get();
        joke.setTitle("Najkrótszy żart o gejach");
        jokeRepository.save(joke);
    }

    @Test
    public void testDelete(){
        if (jokeRepository.existsById(1)){
            jokeRepository.deleteById(1);
        }
    }
}
