package springapp.jokefactory.origin;

import springapp.jokefactory.structure.Structure;

public class OriginFactory {

    public Origin createOrigin(int index) {
        return Origin.builder()
                .id((long) index)
                .name("origin name " + index)
                .build();
    }
}
