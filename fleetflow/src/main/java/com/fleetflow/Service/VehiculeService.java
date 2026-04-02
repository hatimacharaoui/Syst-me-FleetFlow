package com.fleetflow.Service;


import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Dto.VehiculeDto;
import com.fleetflow.Entity.Vehicule;
import com.fleetflow.Mapper.VehiculeMapper;
import com.fleetflow.Repositoryy.VehiculeRepos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService {

    private final VehiculeMapper mapper;
    private final VehiculeRepos repos;

    public VehiculeService(VehiculeMapper mapper, VehiculeRepos repos) {
        this.mapper = mapper;
        this.repos = repos;
    }


    public VehiculeDto addVehicule(VehiculeDto dto){
        Vehicule vehicule=mapper.toEntity(dto);
        return mapper.toDto(repos.save(vehicule));
    }

    public void deleteVehicule(Long id){
       Vehicule vehicule=repos.findById(id)
               .orElseThrow(()->new RuntimeException("Vehicule introvable !!!"));
           repos.delete(vehicule);
    }

    public VehiculeDto updateVehicule(Long id,VehiculeDto vehiculeDto){
        Vehicule vehicule=repos.findById(id)
                .orElseThrow(()->new RuntimeException("Vehicule introvble !!!"));
           mapper.updateVehiculeDto(vehiculeDto,vehicule);
           return mapper.toDto(repos.save(vehicule));
    }

    public List<VehiculeDto>getVehiculesDisponible(){
        List<Vehicule>vehicules=repos.findByDisponibleTrue();
        return mapper.toDto(vehicules);
    }
}
