package springapp.jokefactory.origin;

import org.mapstruct.*;
import springapp.jokefactory.origin.dto.OriginCreatorChildDto;
import springapp.jokefactory.origin.dto.OriginCreatorDto;
import springapp.jokefactory.origin.dto.OriginItemDto;
import springapp.jokefactory.origin.dto.OriginPresenterDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
abstract class OriginMapper {

    @Mapping(target = "parentId", source = "parentId")
    abstract OriginCreatorChildDto mapOriginToOriginCreatorChildDto(Origin origin, Long parentId);

    @Mapping(target = "children", source = "origin", qualifiedByName = "extractCreatorChildDtoList")
    abstract OriginCreatorDto mapOriginToOriginCreatorDto(Origin origin, @Context Set<Origin> connectedOriginSet);

    @Named("extractCreatorChildDtoList")
    List<OriginCreatorChildDto> extractCreatorChildDtoList(Origin origin, @Context Set<Origin> connectedOriginSet) {
        return connectedOriginSet.stream()
                .map(connectedOrigin -> mapOriginToOriginCreatorChildDto(connectedOrigin, origin.getId()))
                .collect(Collectors.toList());
    }

    abstract Origin mapOriginCreatorChildDtoToOrigin(OriginCreatorChildDto originCreatorChildDto);

    @Mapping(target = "children", source = "connectedOriginSet", qualifiedByName = "extractOriginNameSet")
    abstract OriginPresenterDto mapOriginToOriginPresenterDto(Origin origin, Set<Origin> connectedOriginSet);

    @Named("extractOriginNameSet")
    List<String> extractOriginNameSet(Set<Origin> connectedOriginSet) {
        return connectedOriginSet.stream()
                .map(Origin::getName)
                .collect(Collectors.toList());
    }

    abstract OriginItemDto mapOriginToOriginItemDto(Origin origin);

    @Mapping(target = "children", ignore = true)
    abstract Origin mapOriginCreatorDtoToOrigin(OriginCreatorDto originCreatorDto);

}
