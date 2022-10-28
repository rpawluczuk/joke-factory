package springapp.jokefactory.topic.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicPresenterDto {

    private Long id;
    private String name;
    private boolean isCategory;
    private List<String> children;
    private List<String> questions;
    private String dateCreated;
}
