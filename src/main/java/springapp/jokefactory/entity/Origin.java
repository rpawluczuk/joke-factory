package springapp.jokefactory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.serializer.OriginSerializer;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@JsonSerialize(using = OriginSerializer.class)
@Entity
@Data
public class Origin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToMany(mappedBy = "origin", cascade = CascadeType.MERGE)
    @JsonBackReference
    private Set<Joke> jokes;


    @EqualsAndHashCode.Exclude @ToString.Exclude
    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="ORIGIN_CONNECTION",
            joinColumns={@JoinColumn(name="PARENT_ORIGIN_ID")},
            inverseJoinColumns={@JoinColumn(name="CHILD_ORIGIN_ID")})
    private Set<Origin> children = new HashSet<Origin>();

    @JsonSerialize(using = OriginSerializer.class)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @ManyToMany(mappedBy="children")
    private Set<Origin> parents = new HashSet<Origin>();

    @Column(unique=true)
    private String name;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Origin() {
    }

    public Origin(Set<Joke> jokes) {
        this.jokes = jokes;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Origin> getChildren() {
        return children;
    }

    public void setChildren(Set<Origin> children) {
        this.children = children;
    }

    public Set<Origin> getParents() {
        return parents;
    }

    public void setParents(Set<Origin> parents) {
        this.parents = parents;
    }
}
