package com.fleetflow.Controller;


import com.fleetflow.Dto.VehiculeDto;
import com.fleetflow.Service.ClientService;
import com.fleetflow.Service.VehiculeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicule")
public class VehiculeController {
    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping("/afficher/disponible")
    public List<VehiculeDto> afficherVehiculDisponible(){
        return vehiculeService.getVehiculesDisponible();
    }
    @PostMapping("/ajouter")
    public VehiculeDto ajouterVehicule(VehiculeDto dto){
        return vehiculeService.addVehicule(dto);
    }
    @PutMapping("/modifier")
    public VehiculeDto modifierVehicule(@PathVariable Long id,@RequestBody VehiculeDto dto){
        return vehiculeService.updateVehicule(id,dto);
    }
    @DeleteMapping("/supprimer")
    public void supprimerVehicule(@PathVariable Long id){
        vehiculeService.deleteVehicule(id);
    }
}
