package com.fleetflow.Service;

import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Entity.Client;
import com.fleetflow.Mapper.ClientMappper;
import com.fleetflow.Repositoryy.ClientRepos;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

private final ClientMappper mappper;
private final ClientRepos repos;

    public ClientService(ClientMappper mappper, ClientRepos repos) {
        this.mappper = mappper;
        this.repos = repos;
    }


    public ClientDto addClient(ClientDto dto){
        Client client=mappper.toEntity(dto);
        return mappper.toDto(repos.save(client));
    }

    public void deleteClient(Long id){
     Client client=repos.findById(id)
             .orElseThrow(()->new RuntimeException("Client introuvable !"));
          repos.delete(client);
    }

    public ClientDto updateClient(Long id,ClientDto clientDto){
        Client client=repos.findById(id)
                .orElseThrow(()->new RuntimeException("Client introvable !!"));
                mappper.updateClientDto(clientDto,client);
         return mappper.toDto(repos.save(client));
    }

    public List<ClientDto>getAllClient(){
        return mappper.toDto(repos.findAll());
    }
}
