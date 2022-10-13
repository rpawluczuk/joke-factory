package springapp.jokefactory.topic;

import springapp.jokefactory.topic.dto.TopicItemDto;

import java.util.Comparator;

public class TopicItemComparator implements Comparator<TopicItemDto> {

    @Override
    public int compare(TopicItemDto ti1, TopicItemDto ti2) {

        if ("All".equals(ti1.getLabel())) {
            return 1;
        } else {
            return ti1.getLabel().compareTo(ti2.getLabel());
        }
    }
}
