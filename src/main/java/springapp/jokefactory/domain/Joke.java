package springapp.jokefactory.domain;

public class Joke {

    private int id;
    private String title;
    private String content;
    private String author;

    private Structure structure;

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

        if (!getTitle().equals(joke.getTitle())) return false;
        if (!getContent().equals(joke.getContent())) return false;
        return getStructure() != null ? getStructure().equals(joke.getStructure()) : joke.getStructure() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getContent().hashCode();
        result = 31 * result + (getStructure() != null ? getStructure().hashCode() : 0);
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

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}
