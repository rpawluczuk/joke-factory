package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Structure;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StructureRepository {

    List<Structure> structures = new ArrayList<>();

    public void createStrcture(String name, String description) {
        structures.add(new Structure(name, description));
    }

    public List<Structure> getAllStrctures() {
        return structures;
    }

    public void deleteStructure(Structure structure){
        structures.remove(structure);
    }
}
