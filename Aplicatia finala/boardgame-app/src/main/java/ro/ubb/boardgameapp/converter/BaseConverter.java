package ro.ubb.boardgameapp.converter;

import ro.ubb.boardgameapp.dto.BaseDto;
import ro.ubb.boardgameapp.model.BaseEntity;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseConverter<Entity extends BaseEntity<UUID>, Dto extends BaseDto>
        implements Converter<Entity, Dto> {


//    public Set<UUID> convertEntitiesToIDs(Set<Entity> entities) {
//        return entities.stream()
//                .map(model -> entities.getId())
//                .collect(Collectors.toSet());
//    }
//
//    public Set<UUID> convertDTOsToIDs(Set<Dto> dtos) {
//        return dtos.stream()
//                .map(dto -> dto.getId())
//                .collect(Collectors.toSet());
//    }

    public Set<Dto> convertEntitiesToDtos(Collection<Entity> entities) {
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toSet());
    }

//    public Set<Dto> convertEntityToDtos(Entity entity) {
//        return entities.stream()
//                .map(this::convertEntityToDto)
//                .collect(Collectors.toSet());
//    }
}
