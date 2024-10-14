package springapp.jokefactory.topic.view;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicDto;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.dto.TopicItemDto;


@Service
class TopicViewService {

    private final TopicView topicView;
    private final TopicFacade topicFacade;
    private final TopicViewMapper topicViewMapper;
    private final TopicViewFacade topicViewFacade;

    @Autowired
    public TopicViewService(TopicView topicView, TopicFacade topicFacade,
                            TopicViewMapper topicViewMapper, TopicViewFacade topicViewFacade) {
        this.topicView = topicView;
        this.topicFacade = topicFacade;
        this.topicViewMapper = topicViewMapper;
        this.topicViewFacade = topicViewFacade;
    }

    TopicViewDto getTopicView(ViewRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.Direction.ASC, "name");
        Page<Topic> topicPage = topicFacade.getTopicPage(pageRequest);
        return topicViewMapper.mapViewToDto(topicPage);
    }

    void changeCategoryStatus(Long id) {
        Topic topicToEdit = topicFacade.getTopicById(id);
        Topic clonedTopic = SerializationUtils.clone(topicToEdit);
        clonedTopic.setCategory(!clonedTopic.isCategory());
        topicFacade.updateBy(clonedTopic);
    }

//    TopicViewDto changePage(int pageNumber) {
//        PageRequest pageRequest = PageRequest.of(pageNumber, topicView.getTopicPage().getSize(),
//                Sort.Direction.ASC, "name");
//        return topicViewFacade.updateTopicViewPage(pageRequest);
//    }
//
//    TopicViewDto changeSize(int pageSize) {
//        PageRequest pageRequest = PageRequest.of(0, pageSize,
//                Sort.Direction.ASC, "name");
//        return topicViewFacade.updateTopicViewPage(pageRequest);
//    }
//
//    TopicViewDto getTopicViewByName(String name) {
//        topicView.setNameFilter(name);
//        topicView.setCategoryFilter(false);
//        PageRequest pageRequest = PageRequest.of(0, topicView.getTopicPage().getSize(),
//                Sort.Direction.DESC, "dateCreated");
//        return topicViewFacade.updateTopicViewPage(pageRequest);
//    }

//    TopicViewDto changeCategoryFilter() {
//        topicView.setCategoryFilter(!topicView.isCategoryFilter());
//        topicView.setNameFilter("");
//        PageRequest pageRequest = PageRequest.of(0, topicView.getTopicPage().getSize(),
//                Sort.Direction.DESC, "dateCreated");
//        return topicViewFacade.updateTopicViewPage(pageRequest);
//    }

    Iterable<TopicItemDto> getTopicItemList() {
        return topicFacade.getTopicItemList();
    }

    Iterable<TopicItemDto> getCategoryItemList() {
        return topicFacade.getCategoryItemList();
    }

//    @Transactional
//    TopicViewDto refreshTopicView() {
//        PageRequest pageRequest = PageRequest.of(
//                topicView.getTopicPage().getNumber(),
//                topicView.getTopicPage().getSize(),
//                Sort.Direction.DESC, "dateCreated"
//        );
//        Page<Topic> topicPage;
//        if (topicView.isCategoryFilter()) {
//            topicPage = topicFacade.getAllCategoryTopicsPage(pageRequest);
//        } else if (!topicView.getNameFilter().isEmpty()) {
//            topicPage = topicFacade.getTopicPageByName(topicView.getNameFilter(), pageRequest);
//        } else {
//            topicPage = topicFacade.getTopicPage(pageRequest);
//        }
//        topicView.setTopicPage(topicPage);
//        return topicViewMapper.mapViewToDto(topicView);
//    }
}
