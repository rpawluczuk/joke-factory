package springapp.jokefactory.joke.dto;

import lombok.Data;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.structure.dto.StructureItemDto;
import springapp.jokefactory.topicgroup.dto.TopicGroupCreatorDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Data
public class JokeCreatorDto {

    private Long id;
    private Author author;
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
