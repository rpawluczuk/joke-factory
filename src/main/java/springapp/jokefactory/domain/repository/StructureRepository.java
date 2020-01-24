package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.utils.Ids;

import java.util.*;

@Repository
public class StructureRepository {

    List<Structure> structures = new ArrayList<>();

    public void createStrcture(String name, String description) {
        Structure newStructure = new Structure(name, description);
        Set<Integer> idsSet = new TreeSet<>();
        for (Structure structure : structures) {
            idsSet.add(structure.getId());
        }
        newStructure.setId(Ids.generateNewId(idsSet));
        structures.add(newStructure);
    }

    public Collection<Structure> getAllStructures() {
        return structures;
    }

    public void deleteStructure(Structure structure){
        structures.remove(structure);
    }
}
