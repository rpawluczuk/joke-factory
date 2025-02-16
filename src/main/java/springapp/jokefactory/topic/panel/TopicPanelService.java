package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.question.QuestionFacade;
import springapp.jokefactory.question.dto.QuestionItemDto;
import springapp.jokefactory.topic.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
class TopicPanelService {

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private TopicPanel topicPanel;

    @Autowired
    private TopicPanelMapper topicPanelMapper;

    @Autowired
    private QuestionFacade questionFacade;

    private static final Random RANDOM = new Random();
    private static final PageRequest BASIC_PAGE_REQUEST = PageRequest.of(0, 23, Sort.Direction.ASC, "name");

//    TopicPack changeCategoryFilter(Long categoryId, int topicPackIndex) {
//        TopicDto categoryTopic = topicFacade.getTopicDtoById(categoryId);
//        topicPanel.setCategoryFilter(categoryTopic, topicPackIndex);
//        return getFilteredPackByCategory(categoryId, topicPackIndex, BASIC_PAGE_REQUEST);
//    }

    TopicPackDto addTopic(TopicBlockDto topicBlockDto) {
        Topic parentTopic = topicFacade.tryToFindTopicByName(topicBlockDto.getName())
                .orElseGet(() -> createNewTopic(topicBlockDto));
        return TopicPackDtoBuilder
            .builder(topicFacade, topicPanelMapper)
            .withParent(parentTopic)
            .withTopicPackIndex(topicBlockDto.getTopicPackIndex())
            .build();
    }

    TopicPackDto editTopic(TopicBlockDto topicBlockDto) {
        var topic = topicFacade.updateName(topicBlockDto.getId(), topicBlockDto.getName());
        var responseBuilder = TopicPackDtoBuilder
            .builder(topicFacade, topicPanelMapper)
            .withTopicPackIndex(topicBlockDto.getTopicPackIndex());
        if (topicBlockDto.getParentId() == null) {
            return responseBuilder
                .withParent(topic)
                .build();
        } else {
            Topic topicParent = topicFacade.getTopicById(topicBlockDto.getParentId());
            return responseBuilder
                .withParent(topicParent)
                .build();
        }
    }

    private Topic createNewTopic(TopicBlockDto topicBlockDto) {
        Topic topic = topicPanelMapper.toTopic(topicBlockDto);
        if (topicBlockDto.getParentId() == null) {
            return topicFacade.addTopicWithoutParent(topic);
        } else {
            topicFacade.addTopicChild(topic, topicBlockDto.getParentId());
            return topicFacade.getTopicById(topicBlockDto.getParentId());
        }
    }

    TopicPack getTopicPack(Long parentId, PageRequest pageRequest) {
        TopicDto topicParent = topicFacade.getTopicDtoById(parentId);
        Page<Topic> topicPage = topicFacade.getConnectedTopicsPage(parentId, pageRequest);
        Page<TopicBlockDto> topicBlockPage = topicPanelMapper.toBlockPageDto(topicPage, parentId, pageRequest);
        TopicBlockDto topicBlockParent = TopicBlockDto.builder()
                .name(topicParent.getName())
                .topicPackIndex(null)
                .build();
        return TopicPack.builder()
                .build();
    }

    TopicBlockDto getTopicBlock(Long id){
        Topic topic = topicFacade.getTopicById(id);
        return topicPanelMapper.toBlockDto(topic);
    }

    TopicPackDto getPack(PackRequest request) {
        if (request.getParentId() == null) {
            return createInitialPackDto();
        }
        Topic topicParent = topicFacade.getTopicById(request.getParentId());
        var pageRequest = createPageRequest(request.getPageNumber());
        return TopicPackDtoBuilder
            .builder(topicFacade, topicPanelMapper)
            .withParent(topicParent)
            .withTopicPackIndex(request.getTopicPackIndex() + 1)
            .withPageRequest(pageRequest)
            .withSelectedId(request.getSelectedId())
            .build();
    }

    private PageRequest createPageRequest(int pageNumber) {
        return PageRequest.of(pageNumber, 23, Sort.Direction.ASC, "name");
    }

    private TopicPackDto createInitialPackDto() {
        return TopicPackDto.builder()
                .topicBlockParent(
                        TopicBlockDto.builder()
                                .isCategory(false)
                                .build()
                )
                .topicBlockPage(null)
                .build();
    }

//    TopicPack getNewTopicPack(int topicPackIndex, PageRequest pageRequest) {
//        TopicPack topicPack = topicPanel.getTopicPackList().get(topicPackIndex);
//        TopicBlock topicBlockParent = topicPack.getTopicBlockParent();
//        Long parentId = topicBlockParent.getDeprecated_topic().getId();
//        Page<TopicDto> topicPage;
//        if (topicPack.getTopicBlockSecondParent() != null && topicPack.getCategoryFilter() != null) {
//            Long categoryId = topicPack.getCategoryFilter().getId();
//            topicPage = topicFacade.Depracated_getConnectedTopicsPage(parentId, topicPack.getSecondParentId(), categoryId, pageRequest);
//        } else if (topicPack.getTopicBlockSecondParent() != null) {
//            topicPage =  topicFacade.Depracated_getConnectedTopicsPage(parentId, topicPack.getSecondParentId(), pageRequest);
//        } else if (topicPack.getCategoryFilter() != null) {
//            Long categoryId = topicPack.getCategoryFilter().getId();
//            topicPage = topicFacade.getConnectedTopicsByCategory(parentId, categoryId, pageRequest);
//        } else {
//            topicPage = topicFacade.Depracated_getConnectedTopicsPage(parentId, pageRequest);
//        }
//        Page<TopicBlock> topicBlockPage = topicPanelMapper.Depracated_mapToTopicBlockPage(topicPage, parentId, pageRequest);
//        topicBlockPage.getContent().forEach(topicBlock -> topicBlock.setTopicPackIndex(topicPackIndex));
//        return TopicPack.builder()
//                .topicBlockParent(topicBlockParent)
//                .topicBlockPage(topicBlockPage)
//                .build();
//    }

    TopicPack getTopicPack(Long parentId, Long secondParentId, PageRequest pageRequest) {
        Page<TopicDto> topicPage = topicFacade.Depracated_getConnectedTopicsPage(parentId, secondParentId, pageRequest);
        Page<TopicBlock> topicBlockPage = topicPanelMapper.Depracated_mapToTopicBlockPage(topicPage, parentId, pageRequest);
        TopicDto topicParent = topicFacade.getTopicDtoById(parentId);
        TopicBlock topicBlockParent = TopicBlock.builder()
                .Deprecated_topic(topicParent)
                .build();
        return TopicPack.builder()
                .topicBlockParent(topicBlockParent)
                .topicBlockPage(topicBlockPage)
                .build();
    }

    TopicPackDto getPackByPage(int topicPackIndex, int pageNumber) {
        TopicPack consideredTopicPack = topicPanel.getTopicPackList().get(topicPackIndex);
        int pageSize = consideredTopicPack.getPageRequest().getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "name");
        TopicPack topicPack = getTopicPack(consideredTopicPack.getParentId(), pageRequest);
        topicPanel.changeTopicPage(topicPackIndex, topicPack.getTopicBlockPage());
        return topicPanelMapper.mapTopicPackToDto(topicPack);
    }

    List<TopicPackDto> showChildren(int topicPackIndex, Long newlySelectedTopicId) {
        topicPanel.deselectPreviousSecondParentTopic(topicPackIndex);
        topicPanel.selectTopic(topicPackIndex, newlySelectedTopicId);
        TopicPack topicPackChildren = getTopicPack(newlySelectedTopicId, BASIC_PAGE_REQUEST);
        topicPanel.addTopicPack(topicPackChildren, topicPackIndex);
        topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex + 1));
        return List.of(
                topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex)),
                topicPanelMapper.mapTopicPackToDto(topicPackChildren));
    }

    List<TopicPackDto> secondParent(int topicPackIndex, Long secondParentId) {
        topicPanel.selectSecondParentTopic(topicPackIndex, secondParentId);
        TopicBlock selectedTopicBlock = topicPanel.findSelectedTopicBlock(topicPackIndex)
                .orElseThrow(() -> new IllegalArgumentException("no selection found"));
        Long parentId = selectedTopicBlock.getDeprecated_topic().getId();
        TopicPack topicPackChildren = getTopicPack(parentId, secondParentId, BASIC_PAGE_REQUEST);
        topicPanel.addTopicPack(topicPackChildren, topicPackIndex);
        topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex + 1));
        return List.of(
                topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex)),
                topicPanelMapper.mapTopicPackToDto(topicPackChildren));
    }

    List<TopicPackDto> getRandomTopicPack(int topicPackIndex) {

        return List.of();
    }

//    List<TopicPackDto> getRandomTopicPack(int topicPackIndex) {
//        Page<TopicBlock> oldTopicPage = topicPanel.getTopicBlockPage(topicPackIndex);
//        int randomPageNumber = RANDOM.nextInt(oldTopicPage.getTotalPages());
//        PageRequest pageRequest = PageRequest.of(randomPageNumber, oldTopicPage.getSize(), Sort.Direction.ASC, "name");
//        TopicPack randomTopicPack = getNewTopicPack(topicPackIndex, pageRequest);
//        int randomIndex = RANDOM.nextInt(randomTopicPack.getTopicBlockPage().getContent().size());
//        Long randomTopicId = randomTopicPack.getTopicBlockPage().getContent().get(randomIndex).getDeprecated_topic().getId();
//        TopicPack topicPackChildren = getTopicPack(randomTopicId, BASIC_PAGE_REQUEST);
//        topicPanel.selectTopic(topicPackIndex, topicPackChildren.getParentId());
//        topicPanel.addTopicPack(topicPackChildren, topicPackIndex);
//        topicPanel.changeTopicPage(topicPackIndex, randomTopicPack.getTopicBlockPage());
//        return List.of(
//                topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex)),
//                topicPanelMapper.mapTopicPackToDto(topicPackChildren));
//    }

//    TopicPack getFilteredPackByCategory(Long categoryId, int topicPackIndex, PageRequest pageRequest) {
//        TopicBlock topicBlockParent = topicPanel.getTopicBlockParent(topicPackIndex);
//        Page<TopicDto> topicPage = topicFacade.getConnectedTopicsByCategory(topicBlockParent.getDeprecated_topic().getId(), categoryId, pageRequest);
//        Page<TopicBlock> topicBlockPage = topicPanelMapper.Depracated_mapToTopicBlockPage(topicPage, topicBlockParent.getParentId(), pageRequest);
//        topicBlockPage.getContent().forEach(topicBlock -> topicBlock.setTopicPackIndex(topicPackIndex));
//        return topicPanel.changeTopicPage(topicPackIndex, topicBlockPage);
//    }

//    TopicPack getFilterPackByQuestion(Long questionId, int topicPackIndex) {
//        QuestionDto question = questionFacade.getQuestionById(questionId);
//        topicPanel.setQuestionFilter(question, topicPackIndex);
//        TopicDto category = topicFacade.getTopicDtoById(question.getTargetCategory().getValue());
//        topicPanel.setCategoryFilter(category, topicPackIndex);
//        return getFilteredPackByCategory(category.getId(), topicPackIndex, BASIC_PAGE_REQUEST);
//    }

    Iterable<QuestionItemDto> getQuestionItemList(Long topicId) {
        Topic topicParent = topicFacade.getTopicById(topicId);
        List<Question> questionList =
                topicFacade.getConnectedTopicsList(topicParent.getId()).stream()
                        .filter(TopicDto::isCategory)
                        .map((TopicDto sourceCategory) -> questionFacade.getQuestionListBySourceCategory(sourceCategory.getId()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

        return questionList.stream()
                .map(questionFacade::mapQuestionToItemDto)
                .collect(Collectors.toList());
    }
}
