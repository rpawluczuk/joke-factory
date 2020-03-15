//package springapp.jokefactory.services;
//
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.format.Formatter;
//        import org.springframework.stereotype.Service;
//        import springapp.jokefactory.domain.Structure;
//        import springapp.jokefactory.domain.repository.StructureRepository;
//
//        import java.text.ParseException;
//        import java.util.Locale;
//
//@Service
//public class StructureFormatter implements Formatter<Structure> {
//
//    @Autowired
//    StructureRepository structureRepository;
//
//    @Override
//    public Structure parse(String idAsString, Locale locale) throws ParseException {
//        Integer id = Integer.parseInt(idAsString);
//        return structureRepository.getStructure(id);
//    }
//
//    @Override
//    public String print(Structure structure, Locale locale) {
//        return structure.toString();
//    }
//}
