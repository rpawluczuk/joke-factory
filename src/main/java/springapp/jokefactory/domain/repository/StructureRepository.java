package springapp.jokefactory.domain.repository;

import org.springframework.data.repository.CrudRepository;
import springapp.jokefactory.domain.Structure;

public interface StructureRepository extends CrudRepository<Structure, Integer> {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public void createStrcture(String name, String description) {
//        Structure newStructure = new Structure(name, description);
//        entityManager.persist(newStructure);
//    }
//
//    public List<Structure> getAllStructures() {
//        return entityManager.createQuery("from Structure", Structure.class).getResultList();
//    }
//
//    @Transactional
//    public void deleteStructure(Structure structure){
//        entityManager.remove(structure);
//    }
//
//    public Structure getStructure(Integer id) {
//        return entityManager.find(Structure.class, id);
//    }
//
//    @Transactional
//    public void saveStructure(Structure structure) {
//        entityManager.merge(structure);
//    }
}
