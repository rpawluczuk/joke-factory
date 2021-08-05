package springapp.jokefactory.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import springapp.jokefactory.entity.Origin;

import java.io.IOException;
import java.util.Set;

public class ConnectedOriginsSerializer extends StdSerializer<Set<Origin>> {

    public ConnectedOriginsSerializer() {
        this(null);
    }

    public ConnectedOriginsSerializer(Class<Set<Origin>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Origin> origin,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartArray();
        for(Origin childOrigin: origin) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", childOrigin.getId());
            jsonGenerator.writeStringField("name", childOrigin.getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

    }
}
