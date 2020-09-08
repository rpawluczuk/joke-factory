package springapp.jokefactory.Serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import springapp.jokefactory.entity.Joke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomJokeSetSerializer extends StdSerializer<Set<Joke>> {

    public CustomJokeSetSerializer() {
        this(null);
    }

    protected CustomJokeSetSerializer(Class<Set<Joke>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Joke> jokes,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        List<Long> ids = new ArrayList<>();
        for (Joke joke : jokes) {
            ids.add(joke.getId());
        }
        jsonGenerator.writeObject(ids);
    }
}
