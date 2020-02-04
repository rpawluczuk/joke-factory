package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class RoleRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void persistRole(Role role){
        entityManager.persist(role);
    }
}
