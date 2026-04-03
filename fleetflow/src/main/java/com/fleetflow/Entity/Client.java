package com.fleetflow.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String ville;
    private String telephone;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Livraison>livraisons;
}
