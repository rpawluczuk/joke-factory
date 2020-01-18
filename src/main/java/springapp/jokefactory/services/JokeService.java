package springapp.jokefactory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.repository.JokeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class JokeService {

    @Autowired
    JokeRepository jokeRepository;

    public List<Joke> getAllJokes(){
        return new ArrayList<>(jokeRepository.getAllJokes());
    }

    public void saveJoke(Joke joke) {
        jokeRepository.createJoke(joke);
    }

    public Joke getJoke(Integer id) {
        return jokeRepository.getJokeById(id);
    }
}
