package springapp.jokefactory.categorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;
import springapp.jokefactory.topic.TopicFacade;

import java.util.stream.Collectors;

@Service
class CategorizationService {

    @Autowired
    CategorizationRepository categorizationRepository;

    @Autowired
    CategorizationMapper categorizationMapper;

    @Autowired
    TopicFacade topicFacade;

    CategorizationCreatorDto getCategorizationCreator(Long id) {
        Categorization categorization = getCategorizationById(id);
        CategorizationCreatorDto categorizationCreatorDto =
                categorizationMapper.mapCategorizationToCategorizationCreatorDto(categorization);
        categorizationCreatorDto.setBaseCategory(topicFacade.mapTopicToTopicItemDto(categorization.getBaseCategory()));
        categorizationCreatorDto.setLinkedCategory(topicFacade.mapTopicToTopicItemDto(categorization.getLinkedCategory()));
        return categorizationCreatorDto;
    }

    Iterable<CategorizationPresenterDto> getCategorizationPresenterList() {
        return categorizationRepository.findAll().stream()
                .map(categorizationMapper::mapCategorizationToCategorizationPresenterDto)
                .collect(Collectors.toList());
    }

    void addCategorization(CategorizationCreatorDto categorizationCreatorDto) {
        Categorization categorization = new Categorization();
        categorizationMapper.updateCategorizationFromCategorizationCreatorDto(categorizationCreatorDto, categorization);
        topicFacade.tryToGetTopicByTopicItem(categorizationCreatorDto.getBaseCategory()).ifPresent(categorization::setBaseCategory);
        topicFacade.tryToGetTopicByTopicItem(categorizationCreatorDto.getLinkedCategory()).ifPresent(categorization::setLinkedCategory);
        categorizationRepository.save(categorization);
    }

    void editCategorization(CategorizationCreatorDto categorizationCreatorDto) {
        Categorization categorization = getCategorizationById(categorizationCreatorDto.getId());
        categorizationMapper.updateCategorizationFromCategorizationCreatorDto(categorizationCreatorDto, categorization);
        topicFacade.tryToGetTopicByTopicItem(categorizationCreatorDto.getBaseCategory()).ifPresent(categorization::setBaseCategory);
        topicFacade.tryToGetTopicByTopicItem(categorizationCreatorDto.getLinkedCategory()).ifPresent(categorization::setLinkedCategory);
        categorizationRepository.save(categorization);
    }

    void deleteCategorization(Long id) {
        categorizationRepository.delete(getCategorizationById(id));
    }

    private Categorization getCategorizationById(Long id) {
        return categorizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No categorization found with id: " + id));
    }
}
