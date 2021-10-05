package springapp.jokefactory.joke;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import springapp.jokefactory.author.Author;
import springapp.jokefactory.jokeblock.JokeBlock;
import springapp.jokefactory.origin.Origin;
import springapp.jokefactory.structure.Structure;

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
            joinColumns = { @JoinColumn(name = "joke_id") },
            inverseJoinColumns = { @JoinColumn(name = "structure_id") }
            )
    private Set<Structure> structures;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Origin origin;

    @ManyToOne
    private Origin comedyOrigin;

    @ManyToOne
    private Origin ostensibleOrigin;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToMany(mappedBy = "joke", cascade = CascadeType.ALL)
    private List<JokeBlock> jokeBlocks;

    private String title;
    private String content;

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

    public Set<Structure> getStructures() {
        return structures;
    }

    public void setStructures(Set<Structure> structures) {
        this.structures = structures;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public List<JokeBlock> getJokeBlocks() {
        return jokeBlocks;
    }

    public void setJokeBlocks(List<JokeBlock> jokeBlocks) {
        this.jokeBlocks = jokeBlocks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
