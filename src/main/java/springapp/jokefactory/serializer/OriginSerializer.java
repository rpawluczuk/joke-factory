package springapp.jokefactory.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import springapp.jokefactory.entity.Origin;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class OriginSerializer extends StdSerializer<Origin> {

    private final SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public OriginSerializer() {
        this(null);
    }

    public OriginSerializer(Class<Origin> t) {
        super(t);
    }

    @Override
    public void serialize(Origin origin,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", origin.getId());
        jsonGenerator.writeStringField("name", origin.getName());
        jsonGenerator.writeStringField("dateCreated", formatter.format(origin.getDateCreated()));
        jsonGenerator.writeStringField("lastUpdated", formatter.format(origin.getLastUpdated()));


        //Iterate Set
        jsonGenerator.writeArrayFieldStart("children");
        for(Origin childOrigin: origin.getChildren()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", childOrigin.getId());
            jsonGenerator.writeStringField("name", childOrigin.getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
