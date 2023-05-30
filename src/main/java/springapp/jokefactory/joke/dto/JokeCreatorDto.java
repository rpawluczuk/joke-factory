package springapp.jokefactory.joke.dto;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.joke.jokeblock.dto.JokeBlockDto;
import springapp.jokefactory.author.dto.AuthorItemDto;
import springapp.jokefactory.topic.dto.TopicItemDto;
import springapp.jokefactory.algorithm.dto.AlgorithmItemDto;
import springapp.jokefactory.topicgroup.dto.TopicGroupCreatorDto;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class JokeCreatorDto {

    private Long id;
    private AuthorItemDto authorItem;
    private TopicItemDto connectingTopic;
    private TopicItemDto comedyTopic;
    private TopicItemDto ostensibleTopic;
    private List<AlgorithmItemDto> algorithmItemList;
    private List<JokeBlockDto> jokeBlockDtoList;
    private List<TopicGroupCreatorDto> topicGroupCreatorList;
    private String title;
    private String content;
    private Timestamp dateCreated;
}
