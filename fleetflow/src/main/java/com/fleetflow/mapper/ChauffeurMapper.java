package com.fleetflow.mapper;

import com.fleetflow.dto.ChauffeurDTO;
import com.fleetflow.entity.Chauffeur;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChauffeurMapper {
    ChauffeurDTO toDTO(Chauffeur chauffeur);
    Chauffeur toEntity(ChauffeurDTO chauffeurDTO);

    List<ChauffeurDTO> toDTOs(List<Chauffeur> chauffeurs);

//    void update(ChauffeurDTO dto, @MappingTarget Chauffeur entity);
}