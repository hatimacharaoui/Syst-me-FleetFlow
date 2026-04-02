package com.fleetflow.Repositoryy;


import com.fleetflow.Entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepos extends JpaRepository<Vehicule, Long> {
    List<Vehicule>findByDisponibleTrue();
}
