package com.fleetflow.Service;

import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Entity.Client;
import com.fleetflow.Mapper.ClientMapper;
import com.fleetflow.Repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

private final ClientMapper mapper;
private final ClientRepository repos;

    public ClientService(ClientMapper mapper, ClientRepository repos) {
        this.mapper = mapper;
        this.repos = repos;
    }


    public ClientDto addClient(ClientDto dto){
        Client client=mapper.toEntity(dto);
        return mapper.toDto(repos.save(client));
    }

    public void deleteClient(Long id){
     Client client=repos.findById(id)
             .orElseThrow(()->new RuntimeException("Client introuvable !"));
          repos.delete(client);
    }

    public ClientDto updateClient(Long id,ClientDto clientDto){
        Client client=repos.findById(id)
                .orElseThrow(()->new RuntimeException("Client introvable !!"));
                mapper.updateClientDto(clientDto,client);
         return mapper.toDto(repos.save(client));
    }

    public List<ClientDto>getAllClient(){
        return mapper.toDto(repos.findAll());
    }
}
