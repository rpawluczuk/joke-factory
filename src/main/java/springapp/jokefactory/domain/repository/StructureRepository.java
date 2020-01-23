package springapp.jokefactory.domain.repository;

import org.springframework.stereotype.Repository;
import springapp.jokefactory.domain.Joke;
import springapp.jokefactory.domain.Structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class StructureRepository {

    List<Structure> structures = new ArrayList<>();

    public void createStrcture(int id, String name, String description) {
        structures.add(new Structure(id, name, description));
    }

    public Collection<Structure> getAllStructures() {
        return structures;
    }

    public void deleteStructure(Structure structure){
        structures.remove(structure);
    }
}
