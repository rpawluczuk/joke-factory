package springapp.jokefactory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
//import springapp.jokefactory.serializer.ConnectedOriginsSerializer;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
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


////    @JsonSerialize(using = ConnectedOriginsSerializer.class)
//    @OneToMany(mappedBy = "parentOrigin", cascade={CascadeType.PERSIST})
//    private Set<OriginConnection> parents;

    //    @JsonSerialize(using = ConnectedOriginsSerializer.class)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "childOrigin", cascade = CascadeType.MERGE)
//    @JsonBackReference
//    private Set<OriginConnection> children;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "originParent", cascade = CascadeType.MERGE)
    @JsonBackReference
    private Set<OriginRelation> parents;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "originChild", cascade = CascadeType.MERGE)
    @JsonBackReference
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
}
