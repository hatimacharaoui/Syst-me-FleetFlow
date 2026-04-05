package com.fleetflow.Entity;


import com.fleetflow.Enums.StatutVehicule;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vehicule")
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricule;
    private String type;
    private Double capacite;
    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    @OneToMany(mappedBy = "vehicule",cascade = CascadeType.ALL)
    @ToString.Exclude
     private List<Livraison> livraisons;
}
