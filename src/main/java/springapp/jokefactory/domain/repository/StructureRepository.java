package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.utils.Ids;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class StructureRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createStrcture(String name, String description) {
        Structure newStructure = new Structure(name, description);
        entityManager.persist(newStructure);
    }

    public List<Structure> getAllStructures() {
        return entityManager.createQuery("from Structure", Structure.class).getResultList();
    }

    @Transactional
    public void deleteStructure(Structure structure){
        entityManager.remove(structure);
    }

    public Structure getStructure(Integer id) {
        return entityManager.find(Structure.class, id);
    }

    @Transactional
    public void saveStructure(Structure structure) {
        entityManager.merge(structure);
    }
}
