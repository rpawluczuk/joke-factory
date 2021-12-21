package springapp.jokefactory.topic;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.topicgroup.TopicGroup;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "connectingTopic", cascade = CascadeType.MERGE)
    private List<TopicGroup> topicGroupsAsConnecting;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "comedyTopic", cascade = CascadeType.MERGE)
    private List<TopicGroup> topicGroupsAsComedy;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "ostensibleTopic", cascade = CascadeType.MERGE)
    private List<TopicGroup> topicGroupsAsOstensible;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "topicParent", cascade = CascadeType.MERGE)
    private Set<TopicRelation> children;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "topicChild", cascade = CascadeType.MERGE)
    private Set<TopicRelation> parents;

    @Column(unique = true)
    private String name;

    private boolean isCategory;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

}
