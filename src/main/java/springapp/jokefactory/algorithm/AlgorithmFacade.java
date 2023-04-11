package springapp.jokefactory.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmFacade {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    public Algorithm getAlgorithmById(Long id) {
        return algorithmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No algorithm found with id: " + id));
    }
}
