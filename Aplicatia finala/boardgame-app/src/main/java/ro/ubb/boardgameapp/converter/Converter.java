package ro.ubb.boardgameapp.converter;


import ro.ubb.boardgameapp.dto.BaseDto;
import ro.ubb.boardgameapp.model.BaseEntity;

import java.util.UUID;

public interface Converter <Entity extends BaseEntity<UUID>, Dto extends BaseDto> {

   Entity convertDtoToEntity(Dto dto);

    Dto convertEntityToDto(Entity entity);
}
