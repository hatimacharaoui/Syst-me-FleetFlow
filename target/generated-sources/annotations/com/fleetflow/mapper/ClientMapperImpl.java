package com.fleetflow.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T16:29:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDTO toDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDTO.ClientDTOBuilder clientDTO = ClientDTO.builder();

        clientDTO.id( client.getId() );
        clientDTO.nom( client.getNom() );
        clientDTO.email( client.getEmail() );
        clientDTO.ville( client.getVille() );
        clientDTO.telephone( client.getTelephone() );

        return clientDTO.build();
    }

    @Override
    public Client toEntity(ClientDTO clientDTO) {
        if ( clientDTO == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.id( clientDTO.getId() );
        client.nom( clientDTO.getNom() );
        client.email( clientDTO.getEmail() );
        client.ville( clientDTO.getVille() );
        client.telephone( clientDTO.getTelephone() );

        return client.build();
    }
}
