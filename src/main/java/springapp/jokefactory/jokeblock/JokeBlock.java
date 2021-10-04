package springapp.jokefactory.jokeblock;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.joke.Joke;
import springapp.jokefactory.structureblock.StructureBlock;

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
    private StructureBlock structureBlock;

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

    public StructureBlock getStructureBlock() {
        return structureBlock;
    }

    public void setStructureBlock(StructureBlock structureBlock) {
        this.structureBlock = structureBlock;
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
