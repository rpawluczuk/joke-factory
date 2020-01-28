package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.utils.Ids;

import java.util.*;

@Repository
public class StructureRepository {

    Map<Integer, Structure> structures = new HashMap<>();

    public void createStrcture(String name, String description) {
        Structure newStructure = new Structure(name, description);
        newStructure.setId(Ids.generateNewId(structures.keySet()));
        structures.put(newStructure.getId(), newStructure);
    }

    public Collection<Structure> getAllStructures() {
        return structures.values();
    }

    public void deleteStructure(Integer id){
        structures.remove(id);
    }

    public Structure getStructure(Integer id) {
        return structures.get(id);
    }

    public void saveStructure(Structure newStructure) {
        newStructure.setId(Ids.generateNewId(structures.keySet()));
        structures.put(newStructure.getId(), newStructure);
    }
}
