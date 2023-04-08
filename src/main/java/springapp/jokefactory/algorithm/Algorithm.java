package springapp.jokefactory.algorithm;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.algorithm.diagram.DiagramBlock;
import springapp.jokefactory.joke.Joke;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Table(name = "structure")
public class Algorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @ManyToMany(mappedBy = "structures")
    private Set<Joke> jokes;

    private String name;
    private String description;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<DiagramBlock> structureBlockScheme;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Algorithm(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Joke> getJokes() {
        return jokes;
    }

    public void setJokes(Set<Joke> jokes) {
        this.jokes = jokes;
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

    public List<DiagramBlock> getStructureBlockScheme() {
        return structureBlockScheme;
    }

    public void setStructureBlockScheme(List<DiagramBlock> structureBlockScheme) {
        this.structureBlockScheme = structureBlockScheme;
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
