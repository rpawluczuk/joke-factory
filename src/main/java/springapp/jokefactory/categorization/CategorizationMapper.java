package springapp.jokefactory.categorization;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import springapp.jokefactory.categorization.dto.CategorizationCreatorDto;
import springapp.jokefactory.categorization.dto.CategorizationItemDto;
import springapp.jokefactory.categorization.dto.CategorizationPresenterDto;

@Mapper(componentModel = "spring")
abstract class CategorizationMapper {

    @Mapping(target = "connectingCategory", source = "connectingCategory.name")
    @Mapping(target = "ostensibleCategory", source = "ostensibleCategory.name")
    @Mapping(target = "comedyCategory", source = "comedyCategory.name")
    abstract CategorizationPresenterDto mapCategorizationToCategorizationPresenterDto(Categorization categorization);

    @Mapping(target = "text", source = "name")
    abstract CategorizationItemDto mapCategorizationToCategorizationItemDto(Categorization categorization);

    @Mapping(target = "connectingCategory", ignore = true)
    @Mapping(target = "ostensibleCategory", ignore = true)
    @Mapping(target = "comedyCategory", ignore = true)
    abstract CategorizationCreatorDto mapCategorizationToCategorizationCreatorDto(Categorization categorization);

    @Mapping(target = "connectingCategory", ignore = true)
    @Mapping(target = "ostensibleCategory", ignore = true)
    @Mapping(target = "comedyCategory", ignore = true)
    abstract void updateCategorizationFromCategorizationCreatorDto(CategorizationCreatorDto categorizationCreatorDto, @MappingTarget Categorization categorization);
}
