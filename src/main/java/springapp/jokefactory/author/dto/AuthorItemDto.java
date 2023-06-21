package springapp.jokefactory.author.dto;

import lombok.Data;

@Data
public class AuthorItemDto {

    private Long value;
    private String label;

    public AuthorItemDto(String label, Long value) {
        this.label = label;
        this.value = value;
    }
}
