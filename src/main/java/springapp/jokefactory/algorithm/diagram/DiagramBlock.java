package springapp.jokefactory.algorithm.diagram;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import springapp.jokefactory.algorithm.Algorithm;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiagramBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Algorithm algorithm;

//    @EqualsAndHashCode.Exclude @ToString.Exclude
//    @JsonIgnore
//    @OneToMany(mappedBy = "diagramBlock", cascade = CascadeType.ALL)
//    private List<JokeBlock> jokeBlocks;

    private String title;
    private String description;
    private int position;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;
}