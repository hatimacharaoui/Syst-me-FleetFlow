package com.fleetflow.mapper;

import com.fleetflow.dto.LivraisonDTO;
import com.fleetflow.entity.Chauffeur;
import com.fleetflow.entity.Livraison;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T16:29:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class LivraisonMapperImpl implements LivraisonMapper {

    @Override
    public LivraisonDTO toDTO(Livraison livraison) {
        if ( livraison == null ) {
            return null;
        }

        LivraisonDTO.LivraisonDTOBuilder livraisonDTO = LivraisonDTO.builder();

        livraisonDTO.clientId( livraisonClientId( livraison ) );
        livraisonDTO.chauffeurId( livraisonChauffeurId( livraison ) );
        livraisonDTO.vehiculeId( livraisonVehiculeId( livraison ) );
        livraisonDTO.id( livraison.getId() );
        livraisonDTO.dateLivraison( livraison.getDateLivraison() );
        livraisonDTO.adresseDepart( livraison.getAdresseDepart() );
        livraisonDTO.adresseDestination( livraison.getAdresseDestination() );
        livraisonDTO.statut( livraison.getStatut() );

        return livraisonDTO.build();
    }

    @Override
    public Livraison toEntity(LivraisonDTO livraisonDTO) {
        if ( livraisonDTO == null ) {
            return null;
        }

        Livraison.LivraisonBuilder livraison = Livraison.builder();

        livraison.client( livraisonDTOToClient( livraisonDTO ) );
        livraison.chauffeur( livraisonDTOToChauffeur( livraisonDTO ) );
        livraison.vehicule( livraisonDTOToVehicule( livraisonDTO ) );
        livraison.id( livraisonDTO.getId() );
        livraison.dateLivraison( livraisonDTO.getDateLivraison() );
        livraison.adresseDepart( livraisonDTO.getAdresseDepart() );
        livraison.adresseDestination( livraisonDTO.getAdresseDestination() );
        livraison.statut( livraisonDTO.getStatut() );

        return livraison.build();
    }

    private Long livraisonClientId(Livraison livraison) {
        if ( livraison == null ) {
            return null;
        }
        Client client = livraison.getClient();
        if ( client == null ) {
            return null;
        }
        Long id = client.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long livraisonChauffeurId(Livraison livraison) {
        if ( livraison == null ) {
            return null;
        }
        Chauffeur chauffeur = livraison.getChauffeur();
        if ( chauffeur == null ) {
            return null;
        }
        Long id = chauffeur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long livraisonVehiculeId(Livraison livraison) {
        if ( livraison == null ) {
            return null;
        }
        Vehicule vehicule = livraison.getVehicule();
        if ( vehicule == null ) {
            return null;
        }
        Long id = vehicule.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Client livraisonDTOToClient(LivraisonDTO livraisonDTO) {
        if ( livraisonDTO == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.id( livraisonDTO.getClientId() );

        return client.build();
    }

    protected Chauffeur livraisonDTOToChauffeur(LivraisonDTO livraisonDTO) {
        if ( livraisonDTO == null ) {
            return null;
        }

        Chauffeur.ChauffeurBuilder chauffeur = Chauffeur.builder();

        chauffeur.id( livraisonDTO.getChauffeurId() );

        return chauffeur.build();
    }

    protected Vehicule livraisonDTOToVehicule(LivraisonDTO livraisonDTO) {
        if ( livraisonDTO == null ) {
            return null;
        }

        Vehicule.VehiculeBuilder vehicule = Vehicule.builder();

        vehicule.id( livraisonDTO.getVehiculeId() );

        return vehicule.build();
    }
}
