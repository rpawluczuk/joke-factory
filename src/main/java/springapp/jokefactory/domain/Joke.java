package springapp.jokefactory.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotNull
//    @Size(min = 2, max = 50)
    private String title;
//    @NotNull
    private String content;
    private String author;
    private Timestamp creationTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}
