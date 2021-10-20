package springapp.jokefactory.categorization.dto;

import lombok.Data;

@Data
public class CategorizationPresenterDto {

    private Long id;
    private String name;
    private String connectingCategory;
    private String questions;
    private String ostensibleCategory;
    private String comedyCategory;
}
