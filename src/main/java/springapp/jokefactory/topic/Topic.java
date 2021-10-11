package springapp.jokefactory.topic;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.joke.Joke;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Set<Joke> jokes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "comedyTopic", cascade = CascadeType.MERGE)
    private Set<Joke> jokesAsComedyContext;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "ostensibleTopic", cascade = CascadeType.MERGE)
    private Set<Joke> jokesAsOstensibleContext;

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

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Topic(Set<Joke> jokes) {
        this.jokes = jokes;
    }
}
