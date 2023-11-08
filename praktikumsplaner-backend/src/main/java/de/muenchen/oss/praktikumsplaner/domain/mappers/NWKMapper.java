package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NWKMapper {
    NwkDTO toDTO(NWK nwk);

    @Mapping(target = "id", ignore = true)
    NWK toEntity(CreateNwkDTO createNwkDTO);
}