package springapp.jokefactory.topic.panel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import springapp.jokefactory.testutil.factory.PackRequestFactory;
import springapp.jokefactory.testutil.factory.TopicBlockDtoFactory;
import springapp.jokefactory.testutil.factory.TopicFactory;
import springapp.jokefactory.topic.TopicFacade;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicPanelServiceTest {

    @Mock
    private TopicFacade topicFacade;

    @Spy
    private TopicPanelMapper topicPanelMapper;

    @InjectMocks
    private TopicPanelService topicPanelService;

    @Test
    void getInitialTopicPack() {
        // GIVEN
        var initialPackRequest = PackRequestFactory.createInitialRequest();

        // WHEN
        var result = topicPanelService.getPack(initialPackRequest);

        // THEN
        assertNull(result.getTopicBlockParent().getId());
        assertNull(result.getTopicBlockPage());
        assertNull(result.getTopicPackIndex());
        assertNull(result.getSelectedId());
    }

    @Test
    void changeTopicPackByPageWhenIsAnySelection() {
        // GIVEN
        var newPageNumber = 1;
        var packRequest = PackRequestFactory
            .createStandardRequest(request -> request.setPageNumber(newPageNumber));
        var parentTopic = TopicFactory.createParentTopic();
        var pageRequest = PageRequest.of(1, 23, Sort.Direction.ASC, "name");
        var childTopicsPage = TopicFactory.createChildTopicsPage(3, pageRequest);

        when(topicFacade.getTopicById(packRequest.getParentId()))
            .thenReturn(parentTopic);
        when(topicFacade.getConnectedTopicsPage(eq(packRequest.getParentId()), any()))
            .thenReturn(childTopicsPage);

        // WHEN
        var result = topicPanelService.getPack(packRequest);

        // THEN
        verify(topicFacade).getTopicById(packRequest.getParentId());
        verify(topicFacade).getConnectedTopicsPage(eq(packRequest.getParentId()), any());

        assertEquals(packRequest.getParentId(), result.getTopicBlockParent().getId());
        assertEquals(newPageNumber, result.getTopicBlockPage().getNumber());
        assertNull(result.getSelectedId());
    }

    @Test
    void addTopicWithoutParent() {
        // GIVEN
        var inputDto = TopicBlockDtoFactory.createWithoutParent();
        var createdParentTopic = TopicFactory.createParentTopic();

        when(topicFacade.tryToFindTopicByName(inputDto.getName()))
            .thenReturn(Optional.empty());
        when(topicFacade.addTopicWithoutParent(any()))
            .thenReturn(createdParentTopic);
        when(topicFacade.getConnectedTopicsPage(eq(inputDto.getId()), any()))
            .thenReturn(Page.empty());

        // WHEN
        var result = topicPanelService.addTopic(inputDto);

        // THEN
        verify(topicFacade).tryToFindTopicByName(inputDto.getName());
        verify(topicFacade).addTopicWithoutParent(any());
        verify(topicFacade).getConnectedTopicsPage(eq(inputDto.getId()), any());

        assertEquals(inputDto.getId(), result.getTopicBlockParent().getId());
        assertTrue(result.getTopicBlockPage().isEmpty());
        assertEquals(inputDto.getTopicPackIndex(), result.getTopicPackIndex());
    }

    @Test
    void addTopicWithoutParentWhichExistInDB() {
        // GIVEN
        var inputDto = TopicBlockDtoFactory.createWithoutParent();
        var parentTopic = TopicFactory.createParentTopic();

        when(topicFacade.tryToFindTopicByName(inputDto.getName()))
            .thenReturn(Optional.of(parentTopic));
        when(topicFacade.getConnectedTopicsPage(eq(inputDto.getId()), any()))
            .thenReturn(Page.empty());

        // WHEN
        var result = topicPanelService.addTopic(inputDto);

        // THEN
        verify(topicFacade).tryToFindTopicByName(inputDto.getName());
        verify(topicFacade).getConnectedTopicsPage(eq(inputDto.getId()), any());

        assertEquals(inputDto.getId(), result.getTopicBlockParent().getId());
        assertTrue(result.getTopicBlockPage().isEmpty());
        assertEquals(inputDto.getTopicPackIndex(), result.getTopicPackIndex());
    }

    @Test
    void addTopicWithParent() {
        // GIVEN
        var inputDto = TopicBlockDtoFactory.createWithParent();
        var childTopic = TopicFactory.createChildTopic();
        var parentTopic = TopicFactory.createParentTopic();
        var childTopicsPage = TopicFactory.createChildTopicsPage(3, childTopic);

        when(topicFacade.tryToFindTopicByName(inputDto.getName()))
            .thenReturn(Optional.empty());
        when(topicFacade.addTopicChild(any(), eq(inputDto.getParentId())))
            .thenReturn(childTopic);
        when(topicFacade.getTopicById(inputDto.getParentId()))
            .thenReturn(parentTopic);
        when(topicFacade.getConnectedTopicsPage(eq(inputDto.getParentId()), any()))
            .thenReturn(childTopicsPage);

        // WHEN
        var result = topicPanelService.addTopic(inputDto);

        // THEN
        var createdTopicInResult = result.getTopicBlockPage().getContent().stream()
            .filter(topic -> topic.getId().equals(inputDto.getId()))
            .findFirst();

        verify(topicFacade).tryToFindTopicByName(inputDto.getName());
        verify(topicFacade).addTopicChild(any(), eq(inputDto.getParentId()));
        verify(topicFacade).getTopicById(inputDto.getParentId());
        verify(topicFacade).getConnectedTopicsPage(eq(inputDto.getParentId()), any());

        assertTrue(createdTopicInResult.isPresent());
        assertEquals(inputDto.getName(), createdTopicInResult.get().getName());
        assertEquals(inputDto.getTopicPackIndex(), result.getTopicPackIndex());
    }

    @Test
    void updateNameOfTopicWithoutParent() {
        // GIVEN
        var newTopicName = "New Topic Name";
        var inputDto = TopicBlockDtoFactory.createWithoutParent(dto -> dto.setName(newTopicName));
        var updatedTopic = TopicFactory.createParentTopic(topic -> topic.setName(newTopicName));

        when(topicFacade.updateName(inputDto.getId(), newTopicName))
            .thenReturn(updatedTopic);
        when(topicFacade.getConnectedTopicsPage(eq(inputDto.getId()), any()))
            .thenReturn(Page.empty());

        // WHEN
        var result = topicPanelService.editTopic(inputDto);

        // THEN
        verify(topicFacade).updateName(inputDto.getId(), newTopicName);
        verify(topicFacade).getConnectedTopicsPage(eq(inputDto.getId()), any());

        assertEquals(newTopicName, result.getTopicBlockParent().getName());
        assertEquals(inputDto.getTopicPackIndex(), result.getTopicPackIndex());
    }

    @Test
    void updateNameOfTopicWithParent() {
        // GIVEN
        var newTopicName = "New Topic Name";
        var inputDto = TopicBlockDtoFactory.createWithParent(dto -> dto.setName(newTopicName));
        var updatedTopic = TopicFactory.createChildTopic(topic -> topic.setName(newTopicName));
        var parentTopic = TopicFactory.createParentTopic();
        var childTopicsPage = TopicFactory.createChildTopicsPage(3, updatedTopic);

        when(topicFacade.updateName(inputDto.getId(), newTopicName))
            .thenReturn(updatedTopic);
        when(topicFacade.getTopicById(inputDto.getParentId()))
            .thenReturn(parentTopic);
        when(topicFacade.getConnectedTopicsPage(eq(parentTopic.getId()), any()))
            .thenReturn(childTopicsPage);

        // WHEN
        var result = topicPanelService.editTopic(inputDto);

        // THEN
        var updatedTopicInResult = result.getTopicBlockPage().getContent().stream()
            .filter(topic -> topic.getId().equals(inputDto.getId()))
            .findFirst();

        verify(topicFacade).updateName(inputDto.getId(), newTopicName);
        verify(topicFacade).getTopicById(inputDto.getParentId());
        verify(topicFacade).getConnectedTopicsPage(eq(parentTopic.getId()), any());

        assertTrue(updatedTopicInResult.isPresent());
        assertEquals(newTopicName, updatedTopicInResult.get().getName());
        assertEquals(inputDto.getTopicPackIndex(), result.getTopicPackIndex());
        assertEquals(parentTopic.getId(), result.getTopicBlockParent().getId());
    }
}
