package springapp.jokefactory.Serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import springapp.jokefactory.entity.Structure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomStructureSetSerializer extends StdSerializer<Set<Structure>> {

    public CustomStructureSetSerializer() {
        this(null);
    }

    public CustomStructureSetSerializer(Class<Set<Structure>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Structure> structures,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        List<Long> ids = new ArrayList<>();
        for (Structure structure : structures) {
            ids.add(structure.getId());
        }
        jsonGenerator.writeObject(ids);
    }
}
