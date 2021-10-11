package springapp.jokefactory.topic.dto;

import lombok.Data;

@Data
public class TopicCreatorChildDto {

    private Long id;
    private String name;
    private Long parentId;
}
