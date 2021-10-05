package springapp.jokefactory.author.dto;

import lombok.Data;

@Data
public class AuthorCreatorDto {

    private Long id;
    private String name;
    private String surname;
    private String description;
}
