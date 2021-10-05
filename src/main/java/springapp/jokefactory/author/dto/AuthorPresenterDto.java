package springapp.jokefactory.author.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuthorPresenterDto {

    private Long id;
    private String name;
    private String surname;
    private String description;
    private Timestamp dateCreated;
}
