package springapp.jokefactory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Structure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "structure", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Joke> jokes;

    private String name;
    private String description;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Structure() {
    }

    public Structure(String name, String description) {
        this.name = name;
        this.description = description;
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
