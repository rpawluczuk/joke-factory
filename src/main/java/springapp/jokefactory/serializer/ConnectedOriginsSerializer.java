//package springapp.jokefactory.serializer;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//
//import java.io.IOException;
//import java.util.Set;
//
//public class ConnectedOriginsSerializer extends StdSerializer<Set<OriginConnection>> {
//
//    public ConnectedOriginsSerializer() {
//        this(null);
//    }
//
//    public ConnectedOriginsSerializer(Class<Set<OriginConnection>> t) {
//        super(t);
//    }
//
//    @Override
//    public void serialize(Set<OriginConnection> originConnections,
//                          JsonGenerator jsonGenerator,
//                          SerializerProvider serializerProvider) throws IOException {
//
//        jsonGenerator.writeStartArray();
//        for(OriginConnection originConnection: originConnections) {
//            jsonGenerator.writeStartObject();
//            jsonGenerator.writeNumberField("id", 1);
////            jsonGenerator.writeStringField("name", childOrigin.getName());
//            jsonGenerator.writeEndObject();
//        }
//        jsonGenerator.writeEndArray();
//
//    }
//}
