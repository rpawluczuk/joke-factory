package springapp.jokefactory.topic;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.jokeblock.JokeBlock;
import springapp.jokefactory.question.Question;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.topicgroup.TopicGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.LinkedList;
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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "topic", cascade = CascadeType.MERGE)
    private Set<TopicCategory> categories;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
    private List<TopicCategory> topicsOfCategory;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sourceCategory", cascade = CascadeType.MERGE)
    private List<Question> questionsBySource;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "targetCategory", cascade = CascadeType.MERGE)
    private List<Question> questionsByTarget;

    @NotBlank(message = "Name of topic is mandatory")
    @Column(unique = true)
    private String name;

    private boolean isCategory;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public static Topic getBasicTopic() {
        return Topic.builder()
                .id(0L)
                .name("All")
                .isCategory(true)
                .questionsBySource(new LinkedList<>())
                .build();
    }
}
