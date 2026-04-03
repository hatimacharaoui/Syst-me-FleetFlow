package com.fleetflow.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T16:29:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class VehiculeMapperImpl implements VehiculeMapper {

    @Override
    public VehiculeDTO toDTO(Vehicule vehicule) {
        if ( vehicule == null ) {
            return null;
        }

        VehiculeDTO.VehiculeDTOBuilder vehiculeDTO = VehiculeDTO.builder();

        vehiculeDTO.id( vehicule.getId() );
        vehiculeDTO.matricule( vehicule.getMatricule() );
        vehiculeDTO.type( vehicule.getType() );
        vehiculeDTO.capacite( vehicule.getCapacite() );
        vehiculeDTO.statut( vehicule.getStatut() );

        return vehiculeDTO.build();
    }

    @Override
    public Vehicule toEntity(VehiculeDTO vehiculeDTO) {
        if ( vehiculeDTO == null ) {
            return null;
        }

        Vehicule.VehiculeBuilder vehicule = Vehicule.builder();

        vehicule.id( vehiculeDTO.getId() );
        vehicule.matricule( vehiculeDTO.getMatricule() );
        vehicule.type( vehiculeDTO.getType() );
        vehicule.capacite( vehiculeDTO.getCapacite() );
        vehicule.statut( vehiculeDTO.getStatut() );

        return vehicule.build();
    }
}
