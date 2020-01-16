package springapp.jokefactory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.domain.Structure;
import springapp.jokefactory.domain.repository.StructureRepository;
import springapp.jokefactory.domain.repository.JokeRepository;

import java.util.List;

@Service
public class StructureService {

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    StructureRepository structureRepository;

    public void assignStructure(String structureName, String jokeTitle){
        List<Structure> allStructures = structureRepository.getAllStrctures();
        jokeRepository.getJoke(jokeTitle);
    }
}
