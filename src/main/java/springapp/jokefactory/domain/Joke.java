package springapp.jokefactory.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Joke {

    private int id;

    @NotNull
    @Size(min = 2, max = 50)
    private String title;
    @NotNull
    private String content;
    private String author;

    private Integer structureId;

    public Joke() {
        this.author = "unknown";
    }

    public Joke(String title, String content) {
        this.title = title;
        this.content = content;
        this.author = "unknown";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joke)) return false;

        Joke joke = (Joke) o;

        if (getId() != joke.getId()) return false;
        if (getStructureId() != joke.getStructureId()) return false;
        if (!getTitle().equals(joke.getTitle())) return false;
        if (!getContent().equals(joke.getContent())) return false;
        return getAuthor() != null ? getAuthor().equals(joke.getAuthor()) : joke.getAuthor() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getContent().hashCode();
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + getStructureId();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }
}
