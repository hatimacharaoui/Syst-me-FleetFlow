package com.fleetflow.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehiculeDto {
    private Long id;
    @NotBlank(message = "Le matricul est obligatoire")
    private String matricule;
    @NotBlank(message = "Le type est obligatoire")
    private String type;
    @NotNull(message = "La capacite est obligatoire")
    private Double capacite;
    @NotNull(message = "Le statut est obligatoire")
    private String statut;
}
