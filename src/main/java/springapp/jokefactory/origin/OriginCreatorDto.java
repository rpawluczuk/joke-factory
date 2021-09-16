package springapp.jokefactory.origin;

import lombok.Data;

import java.util.List;

@Data
public class OriginCreatorDto {

    private Long id;
    private String name;
    private List<OriginCreatorChildDto> children;
}
