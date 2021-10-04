package springapp.jokefactory.joke;

import lombok.Data;
import springapp.jokefactory.author.Author;
import springapp.jokefactory.jokeblock.JokeBlockCreatorDto;
import springapp.jokefactory.origin.dto.OriginItemDto;
import springapp.jokefactory.structure.StructureItemDto;

import java.sql.Timestamp;
import java.util.List;

@Data
public class JokeCreatorDto {

    private Long id;
    private Author author;
    private OriginItemDto origin;
    private OriginItemDto comedyOrigin;
    private OriginItemDto ostensibleOrigin;
    private List<StructureItemDto> structureItemList;
    private List<JokeBlockCreatorDto> jokeBlockCreatorDtoList;
    private String title;
    private String content;
    private Timestamp dateCreated;
}
