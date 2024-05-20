package springapp.jokefactory.topic.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.question.QuestionFacade;
import springapp.jokefactory.question.dto.QuestionDto;
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

    TopicPack changeCategoryFilter(Long categoryId, int topicPackIndex) {
        TopicDto categoryTopic = topicFacade.getTopicDtoById(categoryId);
        topicPanel.setCategoryFilter(categoryTopic, topicPackIndex);
        return getFilteredPackByCategory(categoryId, topicPackIndex, BASIC_PAGE_REQUEST);
    }

    TopicPanelDto initializeTopicPanel(Long initialId) {
        topicPanel.clearPanel();
        TopicPack topicPack = getTopicPack(initialId, BASIC_PAGE_REQUEST);
        topicPanel.addTopicPack(topicPack);
        return topicPanelMapper.mapTopicPanelToDto(topicPanel);
    }

    TopicPack getTopicPack(Long parentId, PageRequest pageRequest) {
        TopicDto topicParent = topicFacade.getTopicDtoById(parentId);
        Page<TopicDto> topicPage = topicFacade.Depracated_getConnectedTopicsPage(parentId, pageRequest);
        Page<TopicBlock> topicBlockPage = topicPanelMapper.mapTopicDtoPageToTopicBlockPage(topicPage, parentId, pageRequest);
        TopicBlock topicBlockParent = TopicBlock.builder()
                .topic(topicParent)
                .topicPackIndex(null)
                .build();
        return TopicPack.builder()
                .topicBlockParent(topicBlockParent)
                .topicBlockPage(topicBlockPage)
                .build();
    }

    TopicPack getNewTopicPack(int topicPackIndex, PageRequest pageRequest) {
        TopicPack topicPack = topicPanel.getTopicPackList().get(topicPackIndex);
        TopicBlock topicBlockParent = topicPack.getTopicBlockParent();
        Long parentId = topicBlockParent.getTopic().getId();
        Page<TopicDto> topicPage;
        if (topicPack.getTopicBlockSecondParent() != null && topicPack.getCategoryFilter() != null) {
            Long categoryId = topicPack.getCategoryFilter().getId();
            topicPage = topicFacade.Depracated_getConnectedTopicsPage(parentId, topicPack.getSecondParentId(), categoryId, pageRequest);
        } else if (topicPack.getTopicBlockSecondParent() != null) {
            topicPage =  topicFacade.Depracated_getConnectedTopicsPage(parentId, topicPack.getSecondParentId(), pageRequest);
        } else if (topicPack.getCategoryFilter() != null) {
            Long categoryId = topicPack.getCategoryFilter().getId();
            topicPage = topicFacade.getConnectedTopicsByCategory(parentId, categoryId, pageRequest);
        } else {
            topicPage = topicFacade.Depracated_getConnectedTopicsPage(parentId, pageRequest);
        }
        Page<TopicBlock> topicBlockPage = topicPanelMapper.mapTopicDtoPageToTopicBlockPage(topicPage, parentId, pageRequest);
        topicBlockPage.getContent().forEach(topicBlock -> topicBlock.setTopicPackIndex(topicPackIndex));
        return TopicPack.builder()
                .topicBlockParent(topicBlockParent)
                .topicBlockPage(topicBlockPage)
                .build();
    }

    TopicPack getTopicPack(Long parentId, Long secondParentId, PageRequest pageRequest) {
        Page<TopicDto> topicPage = topicFacade.Depracated_getConnectedTopicsPage(parentId, secondParentId, pageRequest);
        Page<TopicBlock> topicBlockPage = topicPanelMapper.mapTopicDtoPageToTopicBlockPage(topicPage, parentId, pageRequest);
        TopicDto topicParent = topicFacade.getTopicDtoById(parentId);
        TopicBlock topicBlockParent = TopicBlock.builder()
                .topic(topicParent)
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
        Long parentId = selectedTopicBlock.getTopic().getId();
        TopicPack topicPackChildren = getTopicPack(parentId, secondParentId, BASIC_PAGE_REQUEST);
        topicPanel.addTopicPack(topicPackChildren, topicPackIndex);
        topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex + 1));
        return List.of(
                topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex)),
                topicPanelMapper.mapTopicPackToDto(topicPackChildren));
    }

    List<TopicPackDto> getRandomTopicPack(int topicPackIndex) {
        Page<TopicBlock> oldTopicPage = topicPanel.getTopicBlockPage(topicPackIndex);
        int randomPageNumber = RANDOM.nextInt(oldTopicPage.getTotalPages());
        PageRequest pageRequest = PageRequest.of(randomPageNumber, oldTopicPage.getSize(), Sort.Direction.ASC, "name");
        TopicPack randomTopicPack = getNewTopicPack(topicPackIndex, pageRequest);
        int randomIndex = RANDOM.nextInt(randomTopicPack.getTopicBlockPage().getContent().size());
        Long randomTopicId = randomTopicPack.getTopicBlockPage().getContent().get(randomIndex).getTopic().getId();
        TopicPack topicPackChildren = getTopicPack(randomTopicId, BASIC_PAGE_REQUEST);
        topicPanel.selectTopic(topicPackIndex, topicPackChildren.getParentId());
        topicPanel.addTopicPack(topicPackChildren, topicPackIndex);
        topicPanel.changeTopicPage(topicPackIndex, randomTopicPack.getTopicBlockPage());
        return List.of(
                topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex)),
                topicPanelMapper.mapTopicPackToDto(topicPackChildren));
    }

    TopicPack getFilteredPackByCategory(Long categoryId, int topicPackIndex, PageRequest pageRequest) {
        TopicBlock topicBlockParent = topicPanel.getTopicBlockParent(topicPackIndex);
        Page<TopicDto> topicPage = topicFacade.getConnectedTopicsByCategory(topicBlockParent.getTopic().getId(), categoryId, pageRequest);
        Page<TopicBlock> topicBlockPage = topicPanelMapper.mapTopicDtoPageToTopicBlockPage(topicPage, topicBlockParent.getParentId(), pageRequest);
        topicBlockPage.getContent().forEach(topicBlock -> topicBlock.setTopicPackIndex(topicPackIndex));
        return topicPanel.changeTopicPage(topicPackIndex, topicBlockPage);
    }

    TopicPack getFilterPackByQuestion(Long questionId, int topicPackIndex) {
        QuestionDto question = questionFacade.getQuestionById(questionId);
        topicPanel.setQuestionFilter(question, topicPackIndex);
        TopicDto category = topicFacade.getTopicDtoById(question.getTargetCategory().getValue());
        topicPanel.setCategoryFilter(category, topicPackIndex);
        return getFilteredPackByCategory(category.getId(), topicPackIndex, BASIC_PAGE_REQUEST);
    }

    Iterable<QuestionItemDto> getQuestionItemList(int topicPackIndex) {
        TopicBlock topicBlockParent = topicPanel.getTopicBlockParent(topicPackIndex);
        List<Question> questionList =
                topicFacade.getConnectedTopicsList(topicBlockParent.getTopic().getId()).stream()
                        .filter(TopicDto::isCategory)
                        .map((TopicDto sourceCategory) -> questionFacade.getQuestionListBySourceCategory(sourceCategory.getId()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

        return questionList.stream()
                .map(questionFacade::mapQuestionToItemDto)
                .collect(Collectors.toList());
    }

    List<TopicPackDto> refreshTopicPack(long parentId) {
        topicPanel.getTopicPackList()
                .forEach(topicPack -> {
                    if (topicPack.getParentId() == (parentId)) {
                        Page<TopicBlock> newTopicBlockPage = getNewTopicPack(topicPack.getTopicPackIndex(), topicPack.getPageRequest()).getTopicBlockPage();
                        Optional<TopicBlock> selectedAsFirstParentId = topicPack.getSelectedAsFirstParent();
                        Optional<TopicBlock> selectedAsSecondParent = topicPack.getSelectedAsSecondParent();
                        newTopicBlockPage.getContent().forEach(topicBlock -> {
                            topicBlock.setTopicPackIndex(topicPack.getTopicPackIndex());
                            if (selectedAsFirstParentId.isPresent() && selectedAsFirstParentId.get().getTopic().getId().equals(topicBlock.getTopic().getId())) {
                                topicBlock.setSelected(true);
                            }
                            if (selectedAsSecondParent.isPresent() && selectedAsSecondParent.get().getTopic().getId().equals(topicBlock.getTopic().getId())) {
                                topicBlock.setSecondParent(true);
                            }
                        });

                        topicPack.setTopicBlockPage(newTopicBlockPage);
                        topicPanel.getTopicPackList().set(topicPack.getTopicPackIndex(), topicPack);
                    }
                });

        return topicPanel.getTopicPackList().stream()
                .filter(tp -> tp.getTopicBlockParent().getTopic().getId() == parentId)
                .map(tp -> topicPanelMapper.mapTopicPackToDto(tp))
                .collect(Collectors.toList());
    }

    TopicPackDto getTopicPack(int topicPackIndex) {
        TopicPack topicPack = topicPanel.getTopicPackList().get(topicPackIndex);
        return topicPanelMapper.mapTopicPackToDto(topicPack);
    }

    TopicPackDto changeSize(int pageSize, int topicPackIndex) {
        PageRequest oldPageRequest = topicPanel.getTopicPackList().get(topicPackIndex).getPageRequest();
        TopicPack topicPack = getNewTopicPack(topicPackIndex, PageRequest.of(0, pageSize, oldPageRequest.getSort()));
        topicPanel.changeTopicPage(topicPackIndex, topicPack.getTopicBlockPage());
        return topicPanelMapper.mapTopicPackToDto(topicPanel.getTopicPackList().get(topicPackIndex));
    }
}
