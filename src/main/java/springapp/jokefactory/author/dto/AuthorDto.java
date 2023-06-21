package springapp.jokefactory.author.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AuthorDto {

    private Long id;
    private String name;
    private String surname;
    private String description;
    private Timestamp dateCreated;
}
