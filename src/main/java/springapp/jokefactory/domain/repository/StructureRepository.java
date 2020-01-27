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
        Set<Integer> idsSet = new TreeSet<>();
        newStructure.setId(Ids.generateNewId(structures.keySet()));
        structures.put(newStructure.getId(), newStructure);
    }

    public Collection<Structure> getAllStructures() {
        return structures.values();
    }

    public void deleteStructure(Structure structure){
        structures.remove(structure);
    }
}
