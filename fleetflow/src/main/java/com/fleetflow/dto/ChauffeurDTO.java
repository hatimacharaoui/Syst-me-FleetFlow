package com.fleetflow.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChauffeurDTO {
    private Long id;
    private String nom;
    private String telephone;
    private String permisType;
    private Boolean disponible;
}