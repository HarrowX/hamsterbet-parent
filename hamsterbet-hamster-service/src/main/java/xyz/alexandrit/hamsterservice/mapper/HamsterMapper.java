package xyz.alexandrit.hamsterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import xyz.alexandrit.common.dto.response.HamsterResponseDTO;
import xyz.alexandrit.hamsterservice.entity.Hamster;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HamsterMapper {
    @Mapping(source = "id", target = "hamsterId")
    HamsterResponseDTO toHamsterResponseDTO(Hamster hamster);
}
