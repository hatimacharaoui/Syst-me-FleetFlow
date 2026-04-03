package com.fleetflow.Mapper;


import com.fleetflow.Dto.VehiculeDto;
import com.fleetflow.Entity.Vehicule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    Vehicule toEntity(VehiculeDto dto);
    VehiculeDto toDto(Vehicule vehicule);
    List<VehiculeDto> toDto(List<Vehicule> vehicules);

    @Mapping(target = "id", ignore = true)
    void updateVehiculeDto(VehiculeDto dto,@MappingTarget Vehicule vehicule);


}
