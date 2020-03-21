package springapp.jokefactory.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Structure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Timestamp creationTime;

    @OneToMany(mappedBy = "structure", cascade = CascadeType.MERGE)
    private Set<Joke> jokes;

    public Structure() {
    }

    public Structure(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Joke> getJokes() {
        return jokes;
    }

    public void setJokes(Set<Joke> jokes) {
        this.jokes = jokes;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public void addJoke(Joke joke){
        if (joke != null){
            if (jokes == null){
                jokes = new HashSet<>();
            }
            joke.setStructure(this);
            jokes.add(joke);
        }
    }

    @Override
    public String toString() {
        return "Structure{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
