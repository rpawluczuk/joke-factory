package springapp.jokefactory.joke.dto;

import lombok.Data;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.structure.dto.StructureItemDto;
import springapp.jokefactory.topicgroup.dto.TopicGroupCreatorDto;

import java.sql.Timestamp;
import java.util.List;

@Data
public class JokeCreatorDto {

    private Long id;
    private AuthorItemDto authorItem;
    private TopicItemDto connectingTopic;
    private TopicItemDto comedyTopic;
    private TopicItemDto ostensibleTopic;
    private List<StructureItemDto> structureItemList;
    private List<JokeBlockCreatorDto> jokeBlockCreatorDtoList;
    private List<TopicGroupCreatorDto> topicGroupCreatorList;
    private String title;
    private String content;
    private Timestamp dateCreated;
}
