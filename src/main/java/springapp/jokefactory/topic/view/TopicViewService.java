package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;
import springapp.jokefactory.topic.dto.TopicItemDto;


@Service
class TopicViewService {

    @Autowired
    private TopicView topicView;

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private TopicViewMapper topicViewMapper;

    @Autowired
    private TopicViewFacade topicViewFacade;

    TopicViewDto getTopicView() {
        return topicViewMapper.mapViewToDto(topicView);
    }

    TopicViewDto changePage(int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, topicView.getTopicPage().getSize(),
                Sort.Direction.ASC, "name");
        return topicViewFacade.updateTopicViewPage(pageRequest);
    }

    TopicViewDto changeSize(int pageSize) {
        PageRequest pageRequest = PageRequest.of(0, pageSize,
                Sort.Direction.ASC, "name");
        return topicViewFacade.updateTopicViewPage(pageRequest);
    }

    TopicViewDto getTopicViewByName(String name) {
        topicView.setNameFilter(name);
        topicView.setCategoryFilter(false);
        PageRequest pageRequest = PageRequest.of(0, topicView.getTopicPage().getSize(),
                Sort.Direction.DESC, "dateCreated");
        return topicViewFacade.updateTopicViewPage(pageRequest);
    }

    TopicViewDto changeCategoryFilter() {
        topicView.setCategoryFilter(!topicView.isCategoryFilter());
        topicView.setNameFilter("");
        PageRequest pageRequest = PageRequest.of(0, topicView.getTopicPage().getSize(),
                Sort.Direction.DESC, "dateCreated");
        return topicViewFacade.updateTopicViewPage(pageRequest);
    }

    Iterable<TopicItemDto> getTopicItemList() {
        return topicFacade.getTopicItemList();
    }

    Iterable<TopicItemDto> getCategoryItemList() {
        return topicFacade.getCategoryItemList();
    }

    @Transactional
    TopicViewDto refreshTopicView() {
        PageRequest pageRequest = PageRequest.of(
                topicView.getTopicPage().getNumber(),
                topicView.getTopicPage().getSize(),
                Sort.Direction.DESC, "dateCreated"
        );
        Page<Topic> topicPage;
        if (topicView.isCategoryFilter()) {
            topicPage = topicFacade.getAllCategoryTopicsPage(pageRequest);
        } else if (!topicView.getNameFilter().isEmpty()) {
            topicPage = topicFacade.getTopicPageByName(topicView.getNameFilter(), pageRequest);
        } else {
            topicPage = topicFacade.getTopicPage(pageRequest);
        }
        topicView.setTopicPage(topicPage);
        return topicViewMapper.mapViewToDto(topicView);
    }
}
