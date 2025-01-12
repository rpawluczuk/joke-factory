package springapp.jokefactory.topic.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewRequest {

    private String nameFilter;
    private Integer pageNumber;
    private Integer pageSize;
}
