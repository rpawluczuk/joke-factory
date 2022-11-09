package springapp.jokefactory.topic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class TopicDto {

    private Long id;
    @NotBlank(message = "Name of topic is mandatory")
    private String name;
    private List<String> categories;
    private List<String> questions;
    private Long parentId;
    private boolean isCategory;
    private boolean isSelected;

    public TopicDto() {
        this.id =  0L;
        this.name = "All";
        this.isCategory = true;
    }
}
