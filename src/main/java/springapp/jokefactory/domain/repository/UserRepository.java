package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void persistUser(User user){
        entityManager.persist(user);
    }
}
