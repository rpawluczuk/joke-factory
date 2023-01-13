package springapp.jokefactory.topic.panel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TopicBlockDto {

    private Long id;
    @NotBlank(message = "Name of topic is mandatory")
    private String name;
    private List<String> categories;
    private List<String> questions;
    private Long parentId;
    private boolean isCategory;
    private boolean isSelected;
    private boolean isSecondParent;
    private Integer topicPackIndex;

    public static TopicBlockDto getBasicTopic() {
        return TopicBlockDto.builder()
                .id(0L)
                .name("All")
                .isCategory(true)
                .build();
    }
}
