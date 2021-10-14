package springapp.jokefactory.categorization;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;

@Mapper(componentModel = "spring")
abstract class CategorizationMapper {

    @Mapping(target = "baseCategory", source = "baseCategory.name")
    @Mapping(target = "linkedCategory", source = "linkedCategory.name")
    abstract CategorizationPresenterDto mapCategorizationToCategorizationPresenterDto(Categorization categorization);

    abstract CategorizationCreatorDto mapCategorizationToCategorizationCreatorDto(Categorization categorization);

    @Mapping(target = "baseCategory", ignore = true)
    @Mapping(target = "linkedCategory", ignore = true)
    abstract void updateCategorizationFromCategorizationCreatorDto(CategorizationCreatorDto categorizationCreatorDto, @MappingTarget Categorization categorization);
}
