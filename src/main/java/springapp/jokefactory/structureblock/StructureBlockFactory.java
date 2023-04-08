package springapp.jokefactory.structureblock;

import springapp.jokefactory.algorithm.diagram.DiagramBlock;
import springapp.jokefactory.algorithm.StructureFactory;

public class StructureBlockFactory {

    private final StructureFactory structureFactory = new StructureFactory();

    public DiagramBlock createStructureBlock(int index, int position) {
        return DiagramBlock.builder()
                .id((long) index)
                .title("structure block title " + index)
                .description("structure block description " + index)
                .position(position)
                .algorithm(structureFactory.createStructure(1))
                .build();
    }
}
