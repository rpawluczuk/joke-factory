package springapp.jokefactory.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import springapp.jokefactory.entity.OriginRelation;

import java.io.IOException;
import java.util.Set;

public class RelatedOriginChildSetSerializer extends StdSerializer<Set<OriginRelation>> {

    public RelatedOriginChildSetSerializer() {
        this(null);
    }

    public RelatedOriginChildSetSerializer(Class<Set<OriginRelation>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<OriginRelation> originRelationSet,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartArray();
        for(OriginRelation originRelation: originRelationSet) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", originRelation.getOriginChild().getId());
            jsonGenerator.writeStringField("name", originRelation.getOriginChild().getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
