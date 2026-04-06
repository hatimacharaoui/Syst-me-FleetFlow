package com.fleetflow.Repository;


import com.fleetflow.Entity.Vehicule;
import com.fleetflow.Enums.StatutVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule>findByStatut(@Param("statut") StatutVehicule statut);
    List<Vehicule>findByCapaciteGreaterThan(@Param("seuil")double seuil);

}
