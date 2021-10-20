package springapp.jokefactory.categorization;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.topicgroup.TopicGroup;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Topic connectingCategory;

    private String questions;

    @ManyToOne
    private Topic ostensibleCategory;

    @ManyToOne
    private Topic comedyCategory;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "categorization", cascade = CascadeType.MERGE)
    private List<TopicGroup> topicGroups;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;
}
