package springapp.jokefactory.origin;

import lombok.Data;

import java.util.List;

@Data
public class OriginCreatorDTO {

    private String name;
    private List<String> children;
}
