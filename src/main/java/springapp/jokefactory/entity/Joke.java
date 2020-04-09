package springapp.jokefactory.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    private String author;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    public Joke() {
        this.author = "unknown";
    }

    public Joke(String title, String content) {
        this.title = title;
        this.content = content;
        this.author = "unknown";
    }
}
