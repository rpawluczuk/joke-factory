package springapp.jokefactory.topic;

import lombok.Builder;
import lombok.Data;
import springapp.jokefactory.question.Question;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class TopicDto {

    private Long id;
    private String name;
    private boolean isCategory;
    private List<TopicDto> children;
    private List<Question> questionsBySource;
    private List<Question> questionsByTarget;

    public static TopicDto getBasicTopic() {
        return TopicDto.builder()
                .id(0L)
                .name("All")
                .isCategory(true)
                .questionsBySource(new LinkedList<>())
                .build();
    }
}
