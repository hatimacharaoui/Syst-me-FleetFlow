package com.fleetflow.Dto;

import lombok.Data;

@Data
public class VehiculeDto {
    private Long id;
    private String matricule;
    private String type;
    private String capacite;
    private String statut;
}
