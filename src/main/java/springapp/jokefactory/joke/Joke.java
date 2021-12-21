package springapp.jokefactory.joke;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import springapp.jokefactory.author.Author;
import springapp.jokefactory.jokeblock.JokeBlock;
import springapp.jokefactory.topic.Topic;
import springapp.jokefactory.structure.Structure;
import springapp.jokefactory.topicgroup.TopicGroup;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinTable(name = "jokes_structures",
            joinColumns = {@JoinColumn(name = "joke_id")},
            inverseJoinColumns = {@JoinColumn(name = "structure_id")}
    )
    private Set<Structure> structures;

    @ManyToOne
    private Author author;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "joke", cascade = CascadeType.ALL)
    private List<JokeBlock> jokeBlocks;

    @ToString.Exclude
    @OneToMany(mappedBy = "joke", cascade = CascadeType.ALL)
    private List<TopicGroup> topicGroups;

    private String title;
    private String content;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "rate_value")),
            @AttributeOverride(name = "count", column = @Column(name = "count_of_rates"))
    })
    private Rate rate;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;
}
