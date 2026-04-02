package com.fleetflow.Entity;


import jakarta.persistence.*;
import lombok.*;

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
    private String capacite;
    private String statut;

    @OneToMany(mappedBy = "vehicule",cascade = CascadeType.ALL)
    @ToString.Exclude
     private List<Livraison>livraisons;
}
