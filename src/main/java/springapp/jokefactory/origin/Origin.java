package springapp.jokefactory.origin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.joke.Joke;
//import springapp.jokefactory.serializer.ConnectedOriginsSerializer;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Origin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "origin", cascade = CascadeType.MERGE)
    @JsonBackReference
    private Set<Joke> jokes;

//    @JsonSerialize(using = RelatedOriginParentSetSerializer.class)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "originChild", cascade = CascadeType.MERGE)
//    private Set<OriginRelation> parents;

    @JsonSerialize(using = RelatedOriginChildSetSerializer.class)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "originParent", cascade = CascadeType.MERGE)
    private Set<OriginRelation> children;

    @Column(unique = true)
    private String name;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Origin(Set<Joke> jokes) {
        this.jokes = jokes;
    }

    public Origin(String originCreatorDTO) {
        this.name = originCreatorDTO;
    }
}
