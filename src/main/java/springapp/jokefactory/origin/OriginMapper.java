package springapp.jokefactory.origin;

import org.mapstruct.*;
import springapp.jokefactory.origin.dto.OriginCreatorChildDto;
import springapp.jokefactory.origin.dto.OriginCreatorDto;
import springapp.jokefactory.origin.dto.OriginItemDto;
import springapp.jokefactory.origin.dto.OriginPresenterDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface OriginMapper {

    @Mapping(target = "parentId", source = "parentId")
    OriginCreatorChildDto mapOriginToOriginCreatorChildDto(Origin origin, Long parentId);

    @Mapping(target = "children", source = "origin", qualifiedByName = "extractCreatorChildDtoList")
    OriginCreatorDto mapOriginToOriginCreatorDto(Origin origin, @Context Set<Origin> connectedOriginSet);

    @Named("extractCreatorChildDtoList")
    default List<OriginCreatorChildDto> extractCreatorChildDtoList(Origin origin, @Context Set<Origin> connectedOriginSet) {
        return connectedOriginSet.stream()
                .map(connectedOrigin -> mapOriginToOriginCreatorChildDto(connectedOrigin, origin.getId()))
                .collect(Collectors.toList());
    }

    Origin mapOriginCreatorChildDtoToOrigin(OriginCreatorChildDto originCreatorChildDto);

    @Mapping(target = "children", source = "connectedOriginSet", qualifiedByName = "extractOriginNameSet")
    OriginPresenterDto mapOriginToOriginPresenterDto(Origin origin, Set<Origin> connectedOriginSet);

    @Named("extractOriginNameSet")
    default List<String> extractOriginNameSet(Set<Origin> connectedOriginSet) {
        return connectedOriginSet.stream()
                .map(Origin::getName)
                .collect(Collectors.toList());
    }

    OriginItemDto mapOriginToOriginItemDto(Origin origin);

    @Mapping(target = "children", ignore = true)
    Origin mapOriginCreatorDtoToOrigin(OriginCreatorDto originCreatorDto);

}
