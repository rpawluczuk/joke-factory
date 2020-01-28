package springapp.jokefactory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.domain.repository.StructureRepository;
import springapp.jokefactory.domain.repository.JokeRepository;

import java.util.ArrayList;

@Service
public class StructureService {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    public ArrayList<Structure> getAllStructures(){
        return new ArrayList<Structure>(structureRepository.getAllStructures());
    }

    public void saveStructure(Structure structure) {
        structureRepository.saveStructure(structure);
    }

    public void deleteStructure(Integer id) {
        structureRepository.deleteStructure(id);
    }
}
