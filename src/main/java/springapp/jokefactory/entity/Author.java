package springapp.jokefactory.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springapp.jokefactory.Serializer.CustomJokeSetSerializer;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    @JsonSerialize(using = CustomJokeSetSerializer.class)
    private Set<Joke> jokes;

    private String name;
    private String surname;
    private String description;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Author() {
    }

    public Author(String name, String surname, String description) {
        this.name = name;
        this.surname = surname;
        this.description = description;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (getId() != null ? !getId().equals(author.getId()) : author.getId() != null) return false;
        if (getJokes() != null ? !getJokes().equals(author.getJokes()) : author.getJokes() != null) return false;
        if (getName() != null ? !getName().equals(author.getName()) : author.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(author.getSurname()) : author.getSurname() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(author.getDescription()) : author.getDescription() != null)
            return false;
        if (getDateCreated() != null ? !getDateCreated().equals(author.getDateCreated()) : author.getDateCreated() != null)
            return false;
        return getLastUpdated() != null ? getLastUpdated().equals(author.getLastUpdated()) : author.getLastUpdated() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getDateCreated() != null ? getDateCreated().hashCode() : 0);
        result = 31 * result + (getLastUpdated() != null ? getLastUpdated().hashCode() : 0);
        return result;
    }
}
