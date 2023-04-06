package springapp.jokefactory.structureblock;

import springapp.jokefactory.algorithm.StructureFactory;

public class StructureBlockFactory {

    private final StructureFactory structureFactory = new StructureFactory();

    public StructureBlock createStructureBlock(int index, int position) {
        return StructureBlock.builder()
                .id((long) index)
                .title("structure block title " + index)
                .description("structure block description " + index)
                .position(position)
                .structure(structureFactory.createStructure(1))
                .build();
    }
}
