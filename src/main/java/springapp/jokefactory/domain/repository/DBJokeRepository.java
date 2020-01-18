package springapp.jokefactory.domain.repository;

import springapp.jokefactory.domain.Joke;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Optional;

public class DBJokeRepository implements JokeRepository {

    @Override
    public void createJoke(String title, String content) {
        System.out.println("Here will be database connection");
        throw new NotImplementedException();
    }

    @Override
    public Collection<Joke> getAllJokes() {
        System.out.println("Here will be database connection");
        throw new NotImplementedException();
    }

    @Override
    public Optional<Joke> getJoke(String title) {
        System.out.println("Here will be database connection");
        throw new NotImplementedException();
    }

    @Override
    public void deleteJoke(Integer id) {
        System.out.println("Here will be database connection");
        throw new NotImplementedException();
    }

    @Override
    public void createJoke(Joke joke) {
        System.out.println("Here will be database connection");
        throw new NotImplementedException();
    }

    @Override
    public Joke getJokeById(Integer id) {
        System.out.println("Here will be database connection");
        throw new NotImplementedException();
    }
}
