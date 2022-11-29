package springapp.jokefactory.topic.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topic.TopicFacade;

@Service
public class TopicViewFacade {

    @Autowired
    private TopicView topicView;

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private TopicViewMapper topicViewMapper;

    @Transactional
    public TopicViewDto updateTopicViewPage(PageRequest pageRequest) {
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

    public void refreshTopicView() {
        PageRequest pageRequest = PageRequest.of(
                topicView.getTopicPage().getNumber(),
                topicView.getTopicPage().getSize(),
                Sort.Direction.DESC, "dateCreated"
        );
        updateTopicViewPage(pageRequest);
    }
}
