package springapp.jokefactory.topic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class TopicCreatorDto {

    private Long id;
    @NotBlank(message = "Name of topic is mandatory")
    private String name;
    private List<String> categories;
    private Long parentId;
}
