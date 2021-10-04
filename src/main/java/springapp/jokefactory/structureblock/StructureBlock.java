package springapp.jokefactory.structureblock;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import springapp.jokefactory.jokeblock.JokeBlock;
import springapp.jokefactory.structure.Structure;

@Entity(name = "structure_block")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StructureBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Structure structure;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "structureBlock", cascade = CascadeType.ALL)
    private List<JokeBlock> jokeBlocks;

    private String title;
    private String description;
    private int position;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<JokeBlock> getJokeBlocks() {
        return jokeBlocks;
    }

    public void setJokeBlocks(List<JokeBlock> jokeBlocks) {
        this.jokeBlocks = jokeBlocks;
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
