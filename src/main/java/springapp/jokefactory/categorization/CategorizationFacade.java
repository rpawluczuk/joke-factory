package springapp.jokefactory.categorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;

@Service
public class CategorizationFacade {

    @Autowired
    private CategorizationRepository categorizationRepository;

    @Autowired
    private CategorizationMapper categorizationMapper;

    public Categorization tryToGetCategorizationById(Long id) {
        return categorizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No categorization found with id: " + id));
    }

    public CategorizationPresenterDto mapCategorizationToCategorizationPresenterDto(Categorization categorization) {
        return categorizationMapper.mapCategorizationToCategorizationPresenterDto(categorization);
    }
}
