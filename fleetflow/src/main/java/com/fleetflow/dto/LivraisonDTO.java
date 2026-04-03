package com.fleetflow.dto;

import com.fleetflow.enums.StatutLivraison;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivraisonDTO {
    private Long id;
    private LocalDate dateLivraison;
    private String adresseDepart;
    private String adresseDestination;
    private StatutLivraison statut;

    private Long clientId;
    private Long chauffeurId;
    private Long vehiculeId;
}