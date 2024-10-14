package springapp.jokefactory.topic.panel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PackRequest {

    private Long parentId;
    private Long selectedId;
    private Integer pageNumber;
    private Integer pageSize;
}
