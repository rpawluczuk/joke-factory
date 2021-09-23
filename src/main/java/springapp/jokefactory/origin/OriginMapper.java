package springapp.jokefactory.origin;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface OriginMapper {

    @Mapping(target = "parentId", source = "parentId")
    OriginCreatorChildDto mapOriginToOriginCreatorChildDto(Origin origin, Long parentId);

    @Mapping(target = "children", source = "origin", qualifiedByName = "extractCreatorChildDtoList")
    OriginCreatorDto mapOriginToOriginCreatorDto(Origin origin, @Context List<Origin> connectedOriginList);

    @Named("extractCreatorChildDtoList")
    default List<OriginCreatorChildDto> extractCreatorChildDtoList(Origin origin, @Context List<Origin> connectedOriginList) {
        return connectedOriginList.stream()
                .map(connectedOrigin -> mapOriginToOriginCreatorChildDto(connectedOrigin, origin.getId()))
                .collect(Collectors.toList());
    }

    Origin mapOriginCreatorChildDtoToOrigin(OriginCreatorChildDto originCreatorChildDto);

    @Mapping(target = "children", source = "connectedOriginSet", qualifiedByName = "extractOriginNameList")
    OriginPresenterDto mapOriginToOriginPresenterDto(Origin origin, Set<Origin> connectedOriginSet);

    @Named("extractOriginNameList")
    default List<String> extractOriginNameList(Set<Origin> connectedOriginList) {
        return connectedOriginList.stream()
                .map(Origin::getName)
                .collect(Collectors.toList());
    }

    OriginItemDto mapOriginToOriginItemDto(Origin origin);

}
