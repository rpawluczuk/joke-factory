package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.joke.JokeFacade;
import springapp.jokefactory.topic.dto.TopicCreatorChildDto;
import springapp.jokefactory.topic.dto.TopicCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.topic.dto.TopicPresenterDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private JokeFacade jokeFacade;

    @Autowired
    private TopicRelationRepository topicRelationRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicFacade topicFacade;

    TopicCreatorDto getTopicCreator(Long id) {
        return topicFacade.tryToGetTopicCreator(id).orElse(null);
    }

    Iterable<TopicPresenterDto> getTopicPresenterList() {
        List<Topic> topicList = topicRepository.findAll();
        return topicList.stream().map(topic -> {
            Set<Topic> connectedTopicList = topicRepository.findAllConnectedTopics(topic);
            return topicMapper.mapTopicToTopicPresenterDto(topic, connectedTopicList);
        }).collect(Collectors.toList());
    }

    Iterable<TopicItemDto> getTopicItemList() {
        return topicRepository.findAll().stream()
                .map(topicMapper::mapTopicToTopicItemDto)
                .collect(Collectors.toList());
    }

    Iterable<TopicCreatorChildDto> getTopicCreatorChildList(Long parentId) {
        Topic topic = topicRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + parentId));
        return topicRepository.findAllConnectedTopics(topic)
                .stream().map(connectedTopic -> topicMapper.mapTopicToTopicCreatorChildDto(connectedTopic, parentId))
                .collect(Collectors.toList());
    }

    void addTopic(TopicCreatorDto topicCreatorDTO) {
        Topic topic = topicRepository.findTopicByName(topicCreatorDTO.getName())
                .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorDtoToTopic(topicCreatorDTO)));
        topicCreatorDTO.getChildren().forEach(topicCreatorDTOChild -> {
            if (!topicCreatorDTOChild.getName().isEmpty()){
                Topic topicChild = topicRepository.findTopicByName(topicCreatorDTOChild.getName())
                        .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorChildDtoToTopic(topicCreatorDTOChild)));
                topicRelationRepository.save(new TopicRelation(topic, topicChild));
            }
        });
    }

    void addTopicChild(TopicCreatorChildDto topicCreatorChildDto) {
        Topic topicParent = topicRepository.findById(topicCreatorChildDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicCreatorChildDto.getParentId()));
        Topic topicChild = topicRepository.findTopicByName(topicCreatorChildDto.getName())
                .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorChildDtoToTopic(topicCreatorChildDto)));
        topicRelationRepository.save(new TopicRelation(topicParent, topicChild));
    }

    void editTopicName(TopicCreatorDto topicCreatorDTO) {
        Topic topicToEdit = topicRepository.findById(topicCreatorDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicCreatorDTO.getId()));
        topicToEdit.setName(topicCreatorDTO.getName());
        topicRepository.save(topicToEdit);
    }

    void deleteTopic(Long id) {
        Topic topicToDelete = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + id));
        jokeFacade.removeTopicFromJokes(topicToDelete);
        topicRelationRepository.findAllTopicRelationsConnectedWithTopic(topicToDelete.getId())
                .forEach(topicRelationRepository::delete);
        topicRepository.delete(topicToDelete);
    }

    void deleteTopicRelation(Long topicParentId, Long topicChildId) {
        TopicRelation topicRelation = topicRelationRepository
                .findById(new TopicRelationKey(topicParentId, topicChildId))
                .orElseThrow(() -> new IllegalArgumentException("No topic relation found with parent id: "
                        + topicParentId + " and child id: " + topicChildId));
        topicRelationRepository.delete(topicRelation);
    }
}
