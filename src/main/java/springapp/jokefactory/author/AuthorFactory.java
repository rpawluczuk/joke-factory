package springapp.jokefactory.author;

import springapp.jokefactory.structure.Structure;

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
