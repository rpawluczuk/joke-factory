package springapp.jokefactory.joke.dto;

import lombok.Data;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.jokeblock.dto.JokeBlockCreatorDto;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.structure.dto.StructureItemDto;

import java.sql.Timestamp;
import java.util.List;

@Data
public class JokeCreatorDto {

    private Long id;
    private Author author;
    private TopicItemDto connectingTopic;
    private TopicItemDto comedyTopic;
    private TopicItemDto ostensibleTopic;
    private List<StructureItemDto> structureItemList;
    private List<JokeBlockCreatorDto> jokeBlockCreatorDtoList;
    private String title;
    private String content;
    private Timestamp dateCreated;
}
