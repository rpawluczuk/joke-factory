package springapp.jokefactory.jokeblock;

import org.springframework.stereotype.Service;

@Service
class JokeBlockService {

//    @Autowired
//    private JokeBlockRepository jokeBlockRepository;
//
//    @Autowired
//    private DiagramFacade structureBlockFacade;
//
//    @Autowired
//    private JokeBlockMapper jokeBlockMapper;

//    Iterable<JokeBlockCreatorDto> getJokeBlockCreatorList(Long jokeId) {
//        List<JokeBlock> jokeBlocks = jokeBlockRepository.findBlocksByJoke(jokeId);
//        List<DiagramBlock> structureBlocks = structureBlockFacade.getAlgorithmDiagram(jokeId);
//        structureBlocks.forEach(structureBlock -> {
//            Optional<JokeBlock> result = jokeBlocks.stream().filter(jokeBlock ->
//                    jokeBlock.getStructureBlock().getId().equals(structureBlock.getId())).findAny();
//            if (!result.isPresent()) {
//                JokeBlock newJokeBlock = new JokeBlock();
//                newJokeBlock.setStructureBlock(structureBlock);
//                jokeBlocks.add(newJokeBlock);
//            }
//        });
//        return jokeBlocks.stream()
//                .map(jokeBlockMapper::jokeBlockToJokeBlockCreatorDto)
//                .collect(Collectors.toList());
//    }

//    List<JokeBlockCreatorDto> getJokeBlockCreatorListOfStructure(Long structureId) {
//        List<DiagramBlock> structureBlocks = structureBlockFacade.getAlgorithmDiagram(structureId);
//        return structureBlocks.stream()
//                .map(jokeBlockMapper::structureBlockToJokeBlockDto)
//                .sorted(Comparator.comparingInt(JokeBlockCreatorDto::getPosition))
//                .collect(Collectors.toList());
//    }
}
