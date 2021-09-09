package springapp.jokefactory.origin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import springapp.jokefactory.origin.OriginRelation;

import java.io.IOException;
import java.util.Set;

public class RelatedOriginParentSetSerializer extends StdSerializer<Set<OriginRelation>> {

    public RelatedOriginParentSetSerializer() {
        this(null);
    }

    public RelatedOriginParentSetSerializer(Class<Set<OriginRelation>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<OriginRelation> originRelationSet,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartArray();
        for(OriginRelation originRelation: originRelationSet) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", originRelation.getOriginParent().getId());
            jsonGenerator.writeStringField("name", originRelation.getOriginParent().getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
