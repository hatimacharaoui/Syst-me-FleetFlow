package com.fleetflow.Controller;

import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/afficher")
    public List<ClientDto> afficherTousClients(){
        return clientService.getAllClient();
    }
    @PostMapping("/ajouter")
    public ClientDto ajouterClient(@RequestBody ClientDto dto){
        return clientService.addClient(dto);
    }
    @PutMapping("/modifier/{id}")
    public ClientDto modifierClient(@PathVariable Long id,@RequestBody ClientDto dto){
        return clientService.updateClient(id,dto);
    }
    @DeleteMapping("/supprimer/{id}")
    public void supprimerClient(@PathVariable Long id){
        clientService.deleteClient(id);
    }
}
