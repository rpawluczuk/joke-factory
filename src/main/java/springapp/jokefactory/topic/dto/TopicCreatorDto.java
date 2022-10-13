package springapp.jokefactory.topic.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicCreatorDto {

    private Long id;
    private String name;
    private List<String> categories;
    private Long parentId;
}
