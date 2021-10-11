package springapp.jokefactory.topic.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicCreatorDto {

    private Long id;
    private String name;
    private List<TopicCreatorChildDto> children;
}
