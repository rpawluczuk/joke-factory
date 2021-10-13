package springapp.jokefactory.categorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;
import springapp.jokefactory.joke.Joke;

import java.util.stream.Collectors;

@Service
class CategorizationService {

    @Autowired
    CategorizationRepository categorizationRepository;

    @Autowired
    CategorizationMapper categorizationMapper;

    void addCategorization(CategorizationCreatorDto categorizationCreatorDto) {
        Categorization categorization = new Categorization();
        categorizationMapper.updateCategorizationFromCategorizationCreatorDto(categorizationCreatorDto, categorization);
        categorizationRepository.save(categorization);
    }

    Iterable<CategorizationPresenterDto> getCategorizationPresenterList() {
        return categorizationRepository.findAll().stream()
                .map(categorizationMapper::mapCategorizationToCategorizationPresenterDto)
                .collect(Collectors.toList());
    }

    void deleteCategorization(Long id) {
        categorizationRepository.delete(getCategorizationById(id));
    }

    private Categorization getCategorizationById(Long id) {
        return categorizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No categorization found with id: " + id));
    }
}
