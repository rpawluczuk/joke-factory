package springapp.jokefactory.topic.panel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackRequest {

    private Long parentId;
    private Long selectedId;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer topicPackIndex;
}
