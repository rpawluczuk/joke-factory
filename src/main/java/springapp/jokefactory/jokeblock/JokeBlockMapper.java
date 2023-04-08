package springapp.jokefactory.jokeblock;

//@Mapper(componentModel = "spring")
//abstract class JokeBlockMapper {
//
//    @Autowired
//    protected DiagramFacade structureBlockFacade;
//
//    @Mapping(target = "structureBlockPresenterDto", source = "jokeBlock.structureBlock")
//    @Mapping(target = "structureId", source = "jokeBlock.structureBlock.structure.id")
//    abstract JokeBlockCreatorDto jokeBlockToJokeBlockCreatorDto(JokeBlock jokeBlock);
//
//    @Mapping(target = "structureId", source = "structureBlock.structure.id")
//    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
//    @Mapping(target = "id", ignore = true)
//    abstract JokeBlockCreatorDto structureBlockToJokeBlockDto(DiagramBlock structureBlock);
//
//    @Mapping(target = "id", source = "jokeBlockCreatorDto.id")
//    @Mapping(target = "joke", source = "joke")
//    @Mapping(target = "structureBlock", source = "jokeBlockCreatorDto.structureBlockPresenterDto", qualifiedByName = "extractStructureBlock")
//    @Mapping(target = "dateCreated", ignore = true)
//    @Mapping(target = "lastUpdated", ignore = true)
//    abstract JokeBlock jokeBlockCreatorDtoToJokeBlock(JokeBlockCreatorDto jokeBlockCreatorDto, Joke joke);
//
//    @Mapping(target = "id", source = "jokeBlockCreatorDto.id")
//    @Mapping(target = "structureBlock", source = "structureBlock")
//    @Mapping(target = "dateCreated", ignore = true)
//    @Mapping(target = "lastUpdated", ignore = true)
//    abstract JokeBlock jokeBlockCreatorDtoToJokeBlock(JokeBlockCreatorDto jokeBlockCreatorDto, DiagramBlock structureBlock);
//
//    @Mapping(target = "structureBlockPresenterDto", source = "structureBlock", qualifiedByName = "extractStructureBlockPresenterDto")
//    @Mapping(target = "structureId", source = "structureBlock.structure.id")
//    abstract JokeBlockPresenterDto jokeBlockToJokeBlockPresenterDto(JokeBlock jokeBlock);

//    @Named("extractStructureBlockPresenterDto")
//    DiagramBlockPresenterDto extractStructureBlockPresenterDto(DiagramBlock structureBlock) {
//        return structureBlockFacade.extractStructureBlockPresenterDto(structureBlock);
//    }
//
//    @Named("extractStructureBlock")
//    DiagramBlock extractStructureBlock(DiagramBlockPresenterDto structureBlockPresenterDto) {
//        return structureBlockFacade.tryToGetStructureBlockByID(structureBlockPresenterDto.getId());
//    }
//}
