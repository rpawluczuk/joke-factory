package springapp.jokefactory.author;

public class AuthorFactory {

    public Author createAuthor() {
        return Author.builder()
                .id(1L)
                .name("author name")
                .surname("author surname")
                .description("author description")
                .build();
    }
}
