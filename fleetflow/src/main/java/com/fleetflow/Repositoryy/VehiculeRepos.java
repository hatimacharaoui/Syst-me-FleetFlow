package com.fleetflow.Repositoryy;


import com.fleetflow.Entity.Vehicule;
import com.fleetflow.Enums.VehiculeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculeRepos extends JpaRepository<Vehicule, Long> {

    @Query("select  v from Vehicule v where v.statut =:statut ")
    List<Vehicule>findByStatut(@Param("statut")VehiculeStatus statut);

    @Query("select v from Vehicule  v where v.capacite> :seuil")
    List<Vehicule>findByCapaciteGreaterThan(@Param("seuil")double seuil);

    VehiculeStatus statut(VehiculeStatus statut);
}
