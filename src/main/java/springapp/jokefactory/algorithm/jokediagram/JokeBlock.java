package springapp.jokefactory.algorithm.jokediagram;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.algorithm.diagram.DiagramBlock;
import springapp.jokefactory.joke.Joke;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "joke_block")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JokeBlock {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private DiagramBlock diagramBlock;

    @ManyToOne(cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Joke joke;

    private String jokeSnippet;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiagramBlock getDiagramBlock() {
        return diagramBlock;
    }

    public void setDiagramBlock(DiagramBlock structureBlock) {
        this.diagramBlock = structureBlock;
    }

    public Joke getJoke() {
        return joke;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }

    public String getJokeSnippet() {
        return jokeSnippet;
    }

    public void setJokeSnippet(String jokeSnippet) {
        this.jokeSnippet = jokeSnippet;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
