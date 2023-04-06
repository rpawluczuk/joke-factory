package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StructureFacade {

    @Autowired
    private AlgorithmRepository structureRepository;

    public Algorithm tryToGetStructureById(Long id) {
        return structureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No structure found with id: " + id));
    }
}
