package springapp.jokefactory.structure;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StructureFactory {

    public Structure createStructure(int index) {
        return Structure.builder()
                .id((long) index)
                .name("structure name " + index)
                .description("structure description " + index)
                .build();
    }

    public Set<Structure> createStructureSet(int initialIndex, int length) {
        return IntStream.range(initialIndex, initialIndex + length)
                .mapToObj(this::createStructure)
                .collect(Collectors.toSet());
    }
}
