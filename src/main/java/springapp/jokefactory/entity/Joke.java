package springapp.jokefactory.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import springapp.jokefactory.deserializer.JokeDeserializer;

//@JsonDeserialize(using = JokeDeserializer.class)
@Entity
@Data
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
//    @JsonManagedReference
    private Author author;

    @ManyToOne
//    @JsonManagedReference
    private Origin origin;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "joke", cascade = CascadeType.ALL)
    private List<JokeBlock> jokeBlocks;

    private String title;
    private String content;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Joke() {
    }

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
