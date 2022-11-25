package springapp.jokefactory.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.dto.*;
import springapp.jokefactory.topic.panel.TopicBlockDto;
import springapp.jokefactory.topic.view.TopicPresenterDto;

import java.util.*;
import java.util.stream.Collectors;

@Service
class TopicPersistanceService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicCategoryRepository topicCategoryRepository;

    @Autowired
    private TopicRelationRepository topicRelationRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private TopicPaginationDto topicPaginationDto;


//    TopicBlockDto getTopic(Long id) {
//        return topicFacade.getTopicDto(id);
//    }

    TopicPaginationDto getTopicPagination() {
        return topicPaginationDto;
    }

//    TopicCreatorDto addTopic(TopicCreatorDto topicCreatorDto) {
//        Topic topic = topicRepository.save(topicMapper.mapTopicCreatorDtoToTopic(topicCreatorDto));
//        return topicMapper.mapTopicToTopicCreatorDto(topic, null);
//    }
//
//    TopicCreatorDto addTopicChild(TopicCreatorDto topicCreatorDto) {
//        Topic topicParent = topicRepository.findById(topicCreatorDto.getParentId())
//                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicCreatorDto.getParentId()));
//        Topic topicChild = topicRepository.findTopicByName(topicCreatorDto.getName())
//                .orElseGet(() -> topicRepository.save(topicMapper.mapTopicCreatorDtoToTopic(topicCreatorDto)));
//        topicRelationRepository.save(new TopicRelation(topicParent, topicChild));
//        if (topicChild.isCategory()) {
//            topicCategoryRepository.save(new TopicCategory(topicParent, topicChild));
//        } else if (topicParent.isCategory()) {
//            topicCategoryRepository.save(new TopicCategory(topicChild, topicParent));
//        }
//        return topicMapper.mapTopicToTopicCreatorDto(topicChild, topicCreatorDto.getParentId());
//    }
//
//    void editTopicName(TopicCreatorDto topicCreatorDTO) {
//        Topic topicToEdit = topicRepository.findById(topicCreatorDTO.getId())
//                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + topicCreatorDTO.getId()));
//        topicToEdit.setName(topicCreatorDTO.getName());
//        topicRepository.save(topicToEdit);
//    }

    void changeCategoryStatus(Long id) {
        Topic topicToEdit = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + id));
        topicToEdit.setCategory(!topicToEdit.isCategory());
        if (topicToEdit.isCategory()){
            topicRepository.findAllConnectedTopics(topicToEdit)
                    .forEach(connectedTopic -> topicCategoryRepository.save(new TopicCategory(connectedTopic, topicToEdit)));
        } else {
            topicCategoryRepository.deleteTopicCategoriesByCategory_Id(topicToEdit.getId());
        }
        topicRepository.save(topicToEdit);
    }

    void updateTopicPagination(TopicPaginationDto topicPaginationDto) {
        this.topicPaginationDto.setCurrentPage(topicPaginationDto.getCurrentPage());
        this.topicPaginationDto.setTotalItems(topicPaginationDto.getTotalItems());
        this.topicPaginationDto.setTotalPages(topicPaginationDto.getTotalPages());
        this.topicPaginationDto.setPageSize(topicPaginationDto.getPageSize());
    }

    void deleteTopic(Long id) {
        Topic topicToDelete = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No topic found with id: " + id));
        topicRelationRepository.deleteAll(topicRelationRepository.findAllTopicRelations(topicToDelete.getId()));
        topicCategoryRepository.deleteTopicCategoriesByTopic_Id(topicToDelete.getId());
        topicRepository.delete(topicToDelete);
    }

    void deleteTopicRelation(Long topicParentId, Long topicChildId) {
        TopicRelation topicRelation = topicRelationRepository
                .findTopicRelationByParentIdAndChildId(topicParentId, topicChildId)
                .orElseThrow(() -> new IllegalArgumentException("No topic relation found with parent id: "
                        + topicParentId + " and child id: " + topicChildId));
        topicRelationRepository.delete(topicRelation);
        topicCategoryRepository
                .findTopicCategoryByParentIdAndChildId(topicParentId, topicChildId)
                .ifPresent(topicCategory -> topicCategoryRepository.delete(topicCategory));
    }

//    Iterable<TopicPresenterDto> getViewByCategorySwitch(boolean isSwitchOn) {
//
//        if (isSwitchOn) {
//            return topicRepository.getAllCategoryTopics().stream().map(topic -> {
//                List<Topic> connectedTopicList = topicRepository.findAllConnectedTopics(topic);
//                return topicMapper.mapTopicToTopicPresenterDto(topic, connectedTopicList);
//            }).collect(Collectors.toList());
//        } else {
//            return getTopicPresenterList();
//        }
//    }
}
