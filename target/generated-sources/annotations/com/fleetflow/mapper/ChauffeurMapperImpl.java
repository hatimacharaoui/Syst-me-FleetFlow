package com.fleetflow.mapper;

import com.fleetflow.dto.ChauffeurDTO;
import com.fleetflow.entity.Chauffeur;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T16:29:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ChauffeurMapperImpl implements ChauffeurMapper {

    @Override
    public ChauffeurDTO toDTO(Chauffeur chauffeur) {
        if ( chauffeur == null ) {
            return null;
        }

        ChauffeurDTO.ChauffeurDTOBuilder chauffeurDTO = ChauffeurDTO.builder();

        chauffeurDTO.id( chauffeur.getId() );
        chauffeurDTO.nom( chauffeur.getNom() );
        chauffeurDTO.telephone( chauffeur.getTelephone() );
        chauffeurDTO.permisType( chauffeur.getPermisType() );
        chauffeurDTO.disponible( chauffeur.getDisponible() );

        return chauffeurDTO.build();
    }

    @Override
    public Chauffeur toEntity(ChauffeurDTO chauffeurDTO) {
        if ( chauffeurDTO == null ) {
            return null;
        }

        Chauffeur.ChauffeurBuilder chauffeur = Chauffeur.builder();

        chauffeur.id( chauffeurDTO.getId() );
        chauffeur.nom( chauffeurDTO.getNom() );
        chauffeur.telephone( chauffeurDTO.getTelephone() );
        chauffeur.permisType( chauffeurDTO.getPermisType() );
        chauffeur.disponible( chauffeurDTO.getDisponible() );

        return chauffeur.build();
    }
}
