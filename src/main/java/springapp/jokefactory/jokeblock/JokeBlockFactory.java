//package springapp.jokefactory.jokeblock;
//
//import springapp.jokefactory.structureblock.StructureBlockFactory;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class JokeBlockFactory {
//
//    private final StructureBlockFactory structureBlockFactory = new StructureBlockFactory();
//
//    public JokeBlock createJokeBlock(int index) {
//        return JokeBlock.builder()
//                .id((long) index)
//                .jokeSnippet("structure name " + index)
//                .structureBlock(structureBlockFactory.createStructureBlock(index, index))
//                .build();
//    }
//
//    public List<JokeBlock> createJokeBlockSet(int initialIndex, int length) {
//        return IntStream.range(initialIndex, initialIndex + length)
//                .mapToObj(this::createJokeBlock)
//                .collect(Collectors.toList());
//    }
//}
