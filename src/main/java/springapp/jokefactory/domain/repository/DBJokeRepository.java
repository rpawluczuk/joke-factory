//package springapp.jokefactory.domain.repository;
//
//import springapp.jokefactory.domain.Joke;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//import java.util.Collection;
//import java.util.Optional;
//
//public class DBJokeRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    @Transactional
//    public void createJoke(String title, String content) {
//        Joke joke = new Joke(title, content);
//        entityManager.persist(joke);
//    }
//
//
//    public Collection<Joke> getAllJokes() {
//        return entityManager.createQuery("from Joke", Joke.class).getResultList();
//    }
//
//
//    public Optional<Joke> getJoke(String title) {
//        Joke jokeByTitle = entityManager.createQuery("from Joke j where j.title=:title", Joke.class)
//                .setParameter("name", title).getSingleResult();
//
//        return Optional.ofNullable(jokeByTitle);
//    }
//
//
//    @Transactional
//    public void deleteJoke(Joke joke) {
//        entityManager.remove(joke);
//    }
//
//
//    @Transactional
//    public void saveJoke(Joke joke) {
//        entityManager.merge(joke);
//    }
//
//
//    public Joke getJokeById(Integer id) {
//        return entityManager.find(Joke.class, id);
//    }
//}
