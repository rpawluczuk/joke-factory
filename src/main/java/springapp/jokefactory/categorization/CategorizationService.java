package springapp.jokefactory.categorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationItemDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.joke.dto.JokeCreatorDto;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.dto.TopicCreatorDto;
import springapp.jokefactory.topicgroup.TopicGroup;
import springapp.jokefactory.topicgroup.dto.TopicGroupCreatorDto;

import java.util.stream.Collectors;

@Service
class CategorizationService {

    @Autowired
    CategorizationRepository categorizationRepository;

    @Autowired
    CategorizationMapper categorizationMapper;

    @Autowired
    TopicFacade topicFacade;

    @Autowired
    JokeFacade jokeFacade;

    CategorizationCreatorDto getCategorizationCreator(Long id) {
        Categorization categorization = getCategorizationById(id);
        CategorizationCreatorDto categorizationCreatorDto =
                categorizationMapper.mapCategorizationToCategorizationCreatorDto(categorization);
        if (categorization.getConnectingCategory() != null) {
            TopicCreatorDto connectingCategory = topicFacade.tryToGetTopicCreator(categorization.getConnectingCategory().getId()).orElse(null);
            categorizationCreatorDto.setConnectingCategory(connectingCategory);
        }
        if (categorization.getOstensibleCategory() != null) {
            TopicCreatorDto ostensibleCategory = topicFacade.tryToGetTopicCreator(categorization.getOstensibleCategory().getId()).orElse(null);
            categorizationCreatorDto.setOstensibleCategory(ostensibleCategory);
        }
        if (categorization.getComedyCategory() != null) {
            TopicCreatorDto comedyCategory = topicFacade.tryToGetTopicCreator(categorization.getComedyCategory().getId()).orElse(null);
            categorizationCreatorDto.setComedyCategory(comedyCategory);
        }
        return categorizationCreatorDto;
    }

    Iterable<CategorizationPresenterDto> getCategorizationPresenterList() {
        return categorizationRepository.findAll().stream()
                .map(categorizationMapper::mapCategorizationToCategorizationPresenterDto)
                .collect(Collectors.toList());
    }

    Iterable<CategorizationItemDto> getCategorizationItemList() {
        return categorizationRepository.findAll().stream()
                .map(categorizationMapper::mapCategorizationToCategorizationItemDto)
                .collect(Collectors.toList());
    }

    Iterable<CategorizationItemDto> getSelectedCategorizationItemList(Long jokeId) {
        return jokeFacade.getJokeById(jokeId)
                .getTopicGroups().stream()
                .map(TopicGroup::getCategorization)
                .map(categorizationMapper::mapCategorizationToCategorizationItemDto)
                .collect(Collectors.toList());
    }

    void addCategorization(CategorizationCreatorDto categorizationCreatorDto) {
        Categorization categorization = new Categorization();
        categorizationMapper.updateCategorizationFromCategorizationCreatorDto(categorizationCreatorDto, categorization);
        topicFacade.tryToGetTopicByTopicCreator(categorizationCreatorDto.getConnectingCategory()).ifPresent(categorization::setConnectingCategory);
        topicFacade.tryToGetTopicByTopicCreator(categorizationCreatorDto.getOstensibleCategory()).ifPresent(categorization::setOstensibleCategory);
        topicFacade.tryToGetTopicByTopicCreator(categorizationCreatorDto.getComedyCategory()).ifPresent(categorization::setComedyCategory);
        categorizationRepository.save(categorization);
    }

    void editCategorization(CategorizationCreatorDto categorizationCreatorDto) {
        Categorization categorization = getCategorizationById(categorizationCreatorDto.getId());
        categorizationMapper.updateCategorizationFromCategorizationCreatorDto(categorizationCreatorDto, categorization);
        topicFacade.tryToGetTopicByTopicCreator(categorizationCreatorDto.getConnectingCategory()).ifPresent(categorization::setConnectingCategory);
        topicFacade.tryToGetTopicByTopicCreator(categorizationCreatorDto.getOstensibleCategory()).ifPresent(categorization::setOstensibleCategory);
        topicFacade.tryToGetTopicByTopicCreator(categorizationCreatorDto.getComedyCategory()).ifPresent(categorization::setComedyCategory);
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
