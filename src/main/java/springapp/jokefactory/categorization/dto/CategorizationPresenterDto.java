package springapp.jokefactory.categorization.dto;

import lombok.Data;

@Data
public class CategorizationPresenterDto {

    private Long id;
    private String name;
    private String baseCategory;
    private String questions;
    private String linkedCategory;
}
