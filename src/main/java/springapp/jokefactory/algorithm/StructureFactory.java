package springapp.jokefactory.algorithm;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StructureFactory {

    public Algorithm createStructure(int index) {
        return Algorithm.builder()
                .id((long) index)
                .name("structure name " + index)
                .description("structure description " + index)
                .build();
    }

    public Set<Algorithm> createStructureSet(int initialIndex, int length) {
        return IntStream.range(initialIndex, initialIndex + length)
                .mapToObj(this::createStructure)
                .collect(Collectors.toSet());
    }
}
