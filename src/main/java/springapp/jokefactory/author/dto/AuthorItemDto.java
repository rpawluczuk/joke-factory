package springapp.jokefactory.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorItemDto {

    private Long id;
    private String text;
}
