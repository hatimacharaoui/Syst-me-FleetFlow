package com.fleetflow.Controller;

⚙️ application.properties
propertiesCopy# ===========================
        # Configuration Base de Données
# ===========================
spring.datasource.url=jdbc:mysql://localhost:3306/fleetflow_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===========================
        # Configuration JPA / Hibernate
# ===========================
        # update = met à jour le schéma automatiquement
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

        # ===========================
        # Configuration Swagger
# ===========================
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# ===========================
        # Port du serveur
# ===========================
server.port=8080

        🎯 Enums
        javaCopy// src/main/java/com/fleetflow/enums/StatutLivraison.java
package com.fleetflow.enums;

// Les 3 états possibles d'une livraison
public enum StatutLivraison {
    ENATTENTE,   // La livraison est créée mais pas encore commencée
    ENCOURS,     // La livraison est en train d'être effectuée
    LIVREE       // La livraison est terminée
}
javaCopy// src/main/java/com/fleetflow/enums/StatutVehicule.java
package com.fleetflow.enums;

// Les 3 états possibles d'un véhicule
public enum StatutVehicule {
    DISPONIBLE,    // Le véhicule peut être assigné
    EN_LIVRAISON,  // Le véhicule est actuellement utilisé
    MAINTENANCE    // Le véhicule est en réparation
}

🏗️ Entités (Entity)

Explication : Les entités sont des classes Java qui représentent les tables de la base de données. Chaque champ = une colonne.

        javaCopy// src/main/java/com/fleetflow/entity/Client.java
package com.fleetflow.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity                    // Dit à JPA que c'est une table
@Table(name = "clients")   // Nom de la table en BDD
@Data                      // Lombok : génère getters, setters, toString, equals
@NoArgsConstructor         // Lombok : génère constructeur sans arguments
@AllArgsConstructor        // Lombok : génère constructeur avec tous les arguments
@Builder                   // Lombok : permet Client.builder().nom("X").build()
public class Client {

    @Id                                                    // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // Auto-incrément
    private Long id;

    @Column(nullable = false)   // La colonne ne peut pas être null
    private String nom;

    @Column(unique = true)      // Email unique dans la table
    private String email;

    private String ville;

    private String telephone;
}
javaCopy// src/main/java/com/fleetflow/entity/Vehicule.java
package com.fleetflow.entity;

import com.fleetflow.enums.StatutVehicule;
import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "vehicules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matricule;      // Ex: "AB-123-CD"

    private String type;           // Ex: "Camion", "Fourgon"

    private Double capacite;       // Capacité en kg ou m³

    @Enumerated(EnumType.STRING)   // Stocke "DISPONIBLE" en texte (pas un nombre)
    private StatutVehicule statut;
}
javaCopy// src/main/java/com/fleetflow/entity/Chauffeur.java
package com.fleetflow.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "chauffeurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chauffeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String telephone;

    private String permisType;     // Ex: "B", "C", "CE"

    private Boolean disponible;    // true = disponible, false = occupé
}
javaCopy// src/main/java/com/fleetflow/entity/Livraison.java
package com.fleetflow.entity;

import com.fleetflow.enums.StatutLivraison;
import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDate;

@Entity
@Table(name = "livraisons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateLivraison;

    private String adresseDepart;

    private String adresseDestination;

    @Enumerated(EnumType.STRING)
    private StatutLivraison statut;

    // ======= RELATIONS =======

    // Plusieurs livraisons peuvent appartenir à un client
    @ManyToOne                          // N livraisons -> 1 client
    @JoinColumn(name = "client_id")     // Crée la colonne FK "client_id"
    private Client client;

    // Plusieurs livraisons peuvent utiliser un chauffeur (à des moments différents)
    @ManyToOne
    @JoinColumn(name = "chauffeur_id")
    private Chauffeur chauffeur;

    // Plusieurs livraisons peuvent utiliser un véhicule (à des moments différents)
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;
}

📦 DTOs (Data Transfer Objects)

Explication : Les DTOs sont des objets qu'on échange avec l'API (entrée/sortie). On ne retourne jamais directement les entités pour éviter d'exposer des données sensibles ou des relations circulaires.

javaCopy// src/main/java/com/fleetflow/dto/ClientDTO.java
package com.fleetflow.dto;

import lombok.*;

// DTO simple pour Client - utilisé en entrée ET en sortie
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private String ville;
    private String telephone;
}
javaCopy// src/main/java/com/fleetflow/dto/VehiculeDTO.java
package com.fleetflow.dto;

import com.fleetflow.enums.StatutVehicule;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculeDTO {
    private Long id;
    private String matricule;
    private String type;
    private Double capacite;
    private StatutVehicule statut;
}
javaCopy// src/main/java/com/fleetflow/dto/ChauffeurDTO.java
package com.fleetflow.dto;

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
javaCopy// src/main/java/com/fleetflow/dto/LivraisonDTO.java
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

    // On retourne juste les IDs des relations (pas les objets complets)
    private Long clientId;
    private Long chauffeurId;
    private Long vehiculeId;
}

🔄 Mappers (MapStruct)

Explication : MapStruct génère automatiquement le code pour convertir Entity ↔ DTO. Il lit vos interfaces et crée l'implémentation à la compilation.

javaCopy// src/main/java/com/fleetflow/mapper/ClientMapper.java
package com.fleetflow.mapper;

import com.fleetflow.dto.ClientDTO;
import com.fleetflow.entity.Client;
import org.mapstruct.Mapper;

// componentModel = "spring" : MapStruct crée un Bean Spring (injectable avec @Autowired)
@Mapper(componentModel = "spring")
public interface ClientMapper {

    // Convertit Client (entity) -> ClientDTO
    ClientDTO toDTO(Client client);

    // Convertit ClientDTO -> Client (entity)
    Client toEntity(ClientDTO clientDTO);
}
javaCopy// src/main/java/com/fleetflow/mapper/VehiculeMapper.java
package com.fleetflow.mapper;

import com.fleetflow.dto.VehiculeDTO;
import com.fleetflow.entity.Vehicule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {
    VehiculeDTO toDTO(Vehicule vehicule);
    Vehicule toEntity(VehiculeDTO vehiculeDTO);
}
javaCopy// src/main/java/com/fleetflow/mapper/ChauffeurMapper.java
package com.fleetflow.mapper;

import com.fleetflow.dto.ChauffeurDTO;
import com.fleetflow.entity.Chauffeur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChauffeurMapper {
    ChauffeurDTO toDTO(Chauffeur chauffeur);
    Chauffeur toEntity(ChauffeurDTO chauffeurDTO);
}
javaCopy// src/main/java/com/fleetflow/mapper/LivraisonMapper.java
package com.fleetflow.mapper;

import com.fleetflow.dto.LivraisonDTO;
import com.fleetflow.entity.Livraison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivraisonMapper {

    // Les champs imbriqués : client.id -> clientId
    // MapStruct utilise la notation "objet.champ"
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "chauffeur.id", target = "chauffeurId")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    LivraisonDTO toDTO(Livraison livraison);

    // Inverse : clientId -> client.id (on crée juste l'objet avec l'id)
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "chauffeurId", target = "chauffeur.id")
    @Mapping(source = "vehiculeId", target = "vehicule.id")
    Livraison toEntity(LivraisonDTO livraisonDTO);
}

🗄️ Repositories

Explication : Les repositories font le lien avec la base de données. Spring Data JPA génère automatiquement les requêtes SQL à partir des noms de méthodes.

javaCopy// src/main/java/com/fleetflow/repository/ClientRepository.java
package com.fleetflow.repository;

import com.fleetflow.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository<Client, Long> = Repository pour Client avec clé primaire de type Long
// Spring génère automatiquement : save(), findById(), findAll(), delete()...
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Pas besoin d'écrire du SQL, Spring comprend le nom de la méthode
}
javaCopy// src/main/java/com/fleetflow/repository/VehiculeRepository.java
package com.fleetflow.repository;

import com.fleetflow.entity.Vehicule;
import com.fleetflow.enums.StatutVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Derived Query : Spring génère -> SELECT * FROM vehicules WHERE statut = ?
    List<Vehicule> findByStatut(StatutVehicule statut);

    // Derived Query : Spring génère -> SELECT * FROM vehicules WHERE capacite > ?
    List<Vehicule> findByCapaciteGreaterThan(Double capacite);
}
javaCopy// src/main/java/com/fleetflow/repository/ChauffeurRepository.java
package com.fleetflow.repository;

import com.fleetflow.entity.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    // Derived Query : Spring génère -> SELECT * FROM chauffeurs WHERE disponible = true
    List<Chauffeur> findByDisponibleTrue();
}
javaCopy// src/main/java/com/fleetflow/repository/LivraisonRepository.java
package com.fleetflow.repository;

import com.fleetflow.entity.Livraison;
import com.fleetflow.enums.StatutLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {

    // Derived Query : par statut
    List<Livraison> findByStatut(StatutLivraison statut);

    // Derived Query : par client ID
    List<Livraison> findByClientId(Long clientId);

    // @Query personnalisée : livraisons entre deux dates
    // JPQL (Java Persistence Query Language) - utilise les noms de classe, pas de table
    @Query("SELECT l FROM Livraison l WHERE l.dateLivraison BETWEEN :dateDebut AND :dateFin")
    List<Livraison> findLivraisonsBetweenDates(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    // @Query personnalisée : livraisons par ville de destination
    @Query("SELECT l FROM Livraison l WHERE l.adresseDestination LIKE %:ville%")
    List<Livraison> findByVilleDestination(@Param("ville") String ville);
}

⚙️ Services

Explication : Le service contient la logique métier. Il utilise le repository pour accéder aux données et le mapper pour convertir les objets.

        javaCopy// src/main/java/com/fleetflow/service/ClientService.java
package com.fleetflow.service;

import com.fleetflow.dto.ClientDTO;
import com.fleetflow.entity.Client;
import com.fleetflow.mapper.ClientMapper;
import com.fleetflow.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service                  // Dit à Spring que c'est un service (Bean géré)
@RequiredArgsConstructor  // Lombok : génère un constructeur avec les champs "final"
public class ClientService {

    // Injection de dépendances via constructeur (bonne pratique)
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    // ===== LISTER TOUS LES CLIENTS =====
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()          // Récupère tous les clients de la BDD
                .stream()                          // Crée un flux de données
                .map(clientMapper::toDTO)          // Convertit chaque Client en ClientDTO
                .collect(Collectors.toList());     // Regroupe en List
    }

    // ===== TROUVER UN CLIENT PAR ID =====
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id: " + id));
        return clientMapper.toDTO(client);
    }

    // ===== CRÉER UN CLIENT =====
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);   // DTO -> Entity
        Client saved = clientRepository.save(client);        // Sauvegarde en BDD
        return clientMapper.toDTO(saved);                    // Entity -> DTO pour la réponse
    }

    // ===== MODIFIER UN CLIENT =====
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        // Vérifie que le client existe
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id: " + id));

        // Met à jour les champs
        existingClient.setNom(clientDTO.getNom());
        existingClient.setEmail(clientDTO.getEmail());
        existingClient.setVille(clientDTO.getVille());
        existingClient.setTelephone(clientDTO.getTelephone());

        Client updated = clientRepository.save(existingClient);
        return clientMapper.toDTO(updated);
    }

    // ===== SUPPRIMER UN CLIENT =====
    public void deleteClient(Long id) {
        // Vérifie que le client existe avant de supprimer
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client non trouvé avec l'id: " + id);
        }
        clientRepository.deleteById(id);
    }
}
javaCopy// src/main/java/com/fleetflow/service/VehiculeService.java
package com.fleetflow.service;

import com.fleetflow.dto.VehiculeDTO;
import com.fleetflow.entity.Vehicule;
import com.fleetflow.enums.StatutVehicule;
import com.fleetflow.mapper.VehiculeMapper;
import com.fleetflow.repository.VehiculeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    public List<VehiculeDTO> getAllVehicules() {
        return vehiculeRepository.findAll()
                .stream()
                .map(vehiculeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VehiculeDTO getVehiculeById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé avec l'id: " + id));
        return vehiculeMapper.toDTO(vehicule);
    }

    public VehiculeDTO createVehicule(VehiculeDTO vehiculeDTO) {
        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDTO);
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    public VehiculeDTO updateVehicule(Long id, VehiculeDTO vehiculeDTO) {
        Vehicule existing = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé avec l'id: " + id));

        existing.setMatricule(vehiculeDTO.getMatricule());
        existing.setType(vehiculeDTO.getType());
        existing.setCapacite(vehiculeDTO.getCapacite());
        existing.setStatut(vehiculeDTO.getStatut());

        return vehiculeMapper.toDTO(vehiculeRepository.save(existing));
    }

    public void deleteVehicule(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new RuntimeException("Véhicule non trouvé avec l'id: " + id);
        }
        vehiculeRepository.deleteById(id);
    }

    // Lister les véhicules disponibles (utilise la derived query)
    public List<VehiculeDTO> getVehiculesDisponibles() {
        return vehiculeRepository.findByStatut(StatutVehicule.DISPONIBLE)
                .stream()
                .map(vehiculeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lister les véhicules par capacité supérieure à une valeur
    public List<VehiculeDTO> getVehiculesByCapacite(Double capacite) {
        return vehiculeRepository.findByCapaciteGreaterThan(capacite)
                .stream()
                .map(vehiculeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
javaCopy// src/main/java/com/fleetflow/service/ChauffeurService.java
package com.fleetflow.service;

import com.fleetflow.dto.ChauffeurDTO;
import com.fleetflow.entity.Chauffeur;
import com.fleetflow.mapper.ChauffeurMapper;
import com.fleetflow.repository.ChauffeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;
    private final ChauffeurMapper chauffeurMapper;

    public List<ChauffeurDTO> getAllChauffeurs() {
        return chauffeurRepository.findAll()
                .stream()
                .map(chauffeurMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ChauffeurDTO getChauffeurById(Long id) {
        Chauffeur chauffeur = chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé avec l'id: " + id));
        return chauffeurMapper.toDTO(chauffeur);
    }

    public ChauffeurDTO createChauffeur(ChauffeurDTO chauffeurDTO) {
        Chauffeur chauffeur = chauffeurMapper.toEntity(chauffeurDTO);
        return chauffeurMapper.toDTO(chauffeurRepository.save(chauffeur));
    }

    public ChauffeurDTO updateChauffeur(Long id, ChauffeurDTO chauffeurDTO) {
        Chauffeur existing = chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé avec l'id: " + id));

        existing.setNom(chauffeurDTO.getNom());
        existing.setTelephone(chauffeurDTO.getTelephone());
        existing.setPermisType(chauffeurDTO.getPermisType());
        existing.setDisponible(chauffeurDTO.getDisponible());

        return chauffeurMapper.toDTO(chauffeurRepository.save(existing));
    }

    public void deleteChauffeur(Long id) {
        if (!chauffeurRepository.existsById(id)) {
            throw new RuntimeException("Chauffeur non trouvé avec l'id: " + id);
        }
        chauffeurRepository.deleteById(id);
    }

    // Lister uniquement les chauffeurs disponibles
    public List<ChauffeurDTO> getChauffeursdisponibles() {
        return chauffeurRepository.findByDisponibleTrue()
                .stream()
                .map(chauffeurMapper::toDTO)
                .collect(Collectors.toList());
    }
}
javaCopy// src/main/java/com/fleetflow/service/LivraisonService.java
package com.fleetflow.service;

import com.fleetflow.dto.LivraisonDTO;
import com.fleetflow.entity.Chauffeur;
import com.fleetflow.entity.Livraison;
import com.fleetflow.entity.Vehicule;
import com.fleetflow.enums.StatutLivraison;
import com.fleetflow.enums.StatutVehicule;
import com.fleetflow.mapper.LivraisonMapper;
import com.fleetflow.repository.ChauffeurRepository;
import com.fleetflow.repository.LivraisonRepository;
import com.fleetflow.repository.VehiculeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivraisonService {

    private final LivraisonRepository livraisonRepository;
    private final LivraisonMapper livraisonMapper;
    private final ChauffeurRepository chauffeurRepository;
    private final VehiculeRepository vehiculeRepository;

    public List<LivraisonDTO> getAllLivraisons() {
        return livraisonRepository.findAll()
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LivraisonDTO getLivraisonById(Long id) {
        Livraison livraison = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée avec l'id: " + id));
        return livraisonMapper.toDTO(livraison);
    }

    // ===== CRÉER UNE LIVRAISON =====
    public LivraisonDTO createLivraison(LivraisonDTO livraisonDTO) {
        Livraison livraison = livraisonMapper.toEntity(livraisonDTO);
        livraison.setStatut(StatutLivraison.ENATTENTE); // Statut initial
        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }

    // ===== ASSIGNER CHAUFFEUR ET VÉHICULE =====
    public LivraisonDTO assignerChauffeurEtVehicule(Long livraisonId,
                                                    Long chauffeurId,
                                                    Long vehiculeId) {
        // 1. Récupère la livraison
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));

        // 2. Récupère le chauffeur
        Chauffeur chauffeur = chauffeurRepository.findById(chauffeurId)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé"));

        // 3. Récupère le véhicule
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        // 4. Vérifie la disponibilité
        if (!chauffeur.getDisponible()) {
            throw new RuntimeException("Le chauffeur n'est pas disponible");
        }
        if (vehicule.getStatut() != StatutVehicule.DISPONIBLE) {
            throw new RuntimeException("Le véhicule n'est pas disponible");
        }

        // 5. Assigne et met à jour les statuts
        livraison.setChauffeur(chauffeur);
        livraison.setVehicule(vehicule);
        livraison.setStatut(StatutLivraison.ENCOURS);

        chauffeur.setDisponible(false);
        vehicule.setStatut(StatutVehicule.EN_LIVRAISON);

        // 6. Sauvegarde tout
        chauffeurRepository.save(chauffeur);
        vehiculeRepository.save(vehicule);

        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }

    // ===== MODIFIER LE STATUT =====
    public LivraisonDTO modifierStatut(Long id, StatutLivraison nouveauStatut) {
        Livraison livraison = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));

        livraison.setStatut(nouveauStatut);

        // Si livraison terminée, libère chauffeur et véhicule
        if (nouveauStatut == StatutLivraison.LIVREE) {
            if (livraison.getChauffeur() != null) {
                livraison.getChauffeur().setDisponible(true);
                chauffeurRepository.save(livraison.getChauffeur());
            }
            if (livraison.getVehicule() != null) {
                livraison.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
                vehiculeRepository.save(livraison.getVehicule());
            }
        }

        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }

    public void deleteLivraison(Long id) {
        if (!livraisonRepository.existsById(id)) {
            throw new RuntimeException("Livraison non trouvée avec l'id: " + id);
        }
        livraisonRepository.deleteById(id);
    }

    // Livraisons entre deux dates
    public List<LivraisonDTO> getLivraisonsBetweenDates(LocalDate debut, LocalDate fin) {
        return livraisonRepository.findLivraisonsBetweenDates(debut, fin)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Livraisons par ville
    public List<LivraisonDTO> getLivraisonsByVille(String ville) {
        return livraisonRepository.findByVilleDestination(ville)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Livraisons par client
    public List<LivraisonDTO> getLivraisonsByClient(Long clientId) {
        return livraisonRepository.findByClientId(clientId)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Livraisons par statut
    public List<LivraisonDTO> getLivraisonsByStatut(StatutLivraison statut) {
        return livraisonRepository.findByStatut(statut)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }
}

🌐 Controllers (API REST)

Explication : Les controllers reçoivent les requêtes HTTP, appellent les services, et retournent les réponses JSON.

javaCopy// src/main/java/com/fleetflow/controller/ClientController.java
package com.fleetflow.controller;

import com.fleetflow.dto.ClientDTO;
import com.fleetflow.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;

@RestController                    // Controller REST = retourne du JSON
@RequestMapping("/api/clients")    // URL de base pour toutes les méthodes
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")  // Swagger
public class ClientController {

    private final ClientService clientService;

    // GET /api/clients -> Récupère tous les clients
    @GetMapping
    @Operation(summary = "Lister tous les clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    // GET /api/clients/1 -> Récupère le client avec id=1
    @GetMapping("/{id}")
    @Operation(summary = "Trouver un client par ID")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    // POST /api/clients -> Crée un nouveau client
    @PostMapping
    @Operation(summary = "Créer un client")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO created = clientService.createClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created
    }

    // PUT /api/clients/1 -> Modifie le client avec id=1
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un client")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id,
                                                  @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.updateClient(id, clientDTO));
    }

    // DELETE /api/clients/1 -> Supprime le client avec id=1
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
javaCopy// src/main/java/com/fleetflow/controller/VehiculeController.java
package com.fleetflow.controller;

import com.fleetflow.dto.VehiculeDTO;
import com.fleetflow.service.VehiculeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @GetMapping
    @Operation(summary = "Lister tous les véhicules")
    public ResponseEntity<List<VehiculeDTO>> getAllVehicules() {
        return ResponseEntity.ok(vehiculeService.getAllVehicules());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un véhicule par ID")
    public ResponseEntity<VehiculeDTO> getVehiculeById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.getVehiculeById(id));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les véhicules disponibles")
    public ResponseEntity<List<VehiculeDTO>> getVehiculesDisponibles() {
        return ResponseEntity.ok(vehiculeService.getVehiculesDisponibles());
    }

    // GET /api/vehicules/capacite?min=1000 -> Véhicules avec capacité > 1000
    @GetMapping("/capacite")
    @Operation(summary = "Lister les véhicules par capacité minimale")
    public ResponseEntity<List<VehiculeDTO>> getByCapacite(@RequestParam Double min) {
        return ResponseEntity.ok(vehiculeService.getVehiculesByCapacite(min));
    }

    @PostMapping
    @Operation(summary = "Ajouter un véhicule")
    public ResponseEntity<VehiculeDTO> createVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculeService.createVehicule(vehiculeDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule")
    public ResponseEntity<VehiculeDTO> updateVehicule(@PathVariable Long id,
                                                      @RequestBody VehiculeDTO vehiculeDTO) {
        return ResponseEntity.ok(vehiculeService.updateVehicule(id, vehiculeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        vehiculeService.deleteVehicule(id);
        return ResponseEntity.noContent().build();
    }
}
javaCopy// src/main/java/com/fleetflow/controller/ChauffeurController.java
package com.fleetflow.controller;

import com.fleetflow.dto.ChauffeurDTO;
import com.fleetflow.service.ChauffeurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;

@RestController
@RequestMapping("/api/chauffeurs")
@RequiredArgsConstructor
@Tag(name = "Chauffeurs", description = "Gestion des chauffeurs")
public class ChauffeurController {

    private final ChauffeurService chauffeurService;

    @GetMapping
    @Operation(summary = "Lister tous les chauffeurs")
    public ResponseEntity<List<ChauffeurDTO>> getAllChauffeurs() {
        return ResponseEntity.ok(chauffeurService.getAllChauffeurs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un chauffeur par ID")
    public ResponseEntity<ChauffeurDTO> getChauffeurById(@PathVariable Long id) {
        return ResponseEntity.ok(chauffeurService.getChauffeurById(id));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les chauffeurs disponibles")
    public ResponseEntity<List<ChauffeurDTO>> getChauffeursDisponibles() {
        return ResponseEntity.ok(chauffeurService.getChauffeursdisponibles());
    }

    @PostMapping
    @Operation(summary = "Ajouter un chauffeur")
    public ResponseEntity<ChauffeurDTO> createChauffeur(@RequestBody ChauffeurDTO chauffeurDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chauffeurService.createChauffeur(chauffeurDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un chauffeur")
    public ResponseEntity<ChauffeurDTO> updateChauffeur(@PathVariable Long id,
                                                        @RequestBody ChauffeurDTO chauffeurDTO) {
        return ResponseEntity.ok(chauffeurService.updateChauffeur(id, chauffeurDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un chauffeur")
    public ResponseEntity<Void> deleteChauffeur(@PathVariable Long id) {
        chauffeurService.deleteChauffeur(id);
        return ResponseEntity.noContent().build();
    }
}
javaCopy// src/main/java/com/fleetflow/controller/LivraisonController.java
package com.fleetflow.controller;

import com.fleetflow.dto.LivraisonDTO;
import com.fleetflow.enums.StatutLivraison;
import com.fleetflow.service.LivraisonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
@RequiredArgsConstructor
@Tag(name = "Livraisons", description = "Gestion des livraisons")
public class LivraisonController {

    private final LivraisonService livraisonService;

    @GetMapping
    @Operation(summary = "Lister toutes les livraisons")
    public ResponseEntity<List<LivraisonDTO>> getAllLivraisons() {
        return ResponseEntity.ok(livraisonService.getAllLivraisons());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver une livraison par ID")
    public ResponseEntity<LivraisonDTO> getLivraisonById(@PathVariable Long id) {
        return ResponseEntity.ok(livraisonService.getLivraisonById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une livraison")
    public ResponseEntity<LivraisonDTO> createLivraison(@RequestBody LivraisonDTO livraisonDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livraisonService.createLivraison(livraisonDTO));
    }

    // PUT /api/livraisons/1/assigner?chauffeurId=2&vehiculeId=3
    @PutMapping("/{id}/assigner")
    @Operation(summary = "Assigner un chauffeur et un véhicule à une livraison")
    public ResponseEntity<LivraisonDTO> assigner(
            @PathVariable Long id,
            @RequestParam Long chauffeurId,
            @RequestParam Long vehiculeId) {
        return ResponseEntity.ok(
                livraisonService.assignerChauffeurEtVehicule(id, chauffeurId, vehiculeId)
        );
    }

    // PUT /api/livraisons/1/statut?statut=LIVREE
    @PutMapping("/{id}/statut")
    @Operation(summary = "Modifier le statut d'une livraison")
    public ResponseEntity<LivraisonDTO> modifierStatut(
            @PathVariable Long id,
            @RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.modifierStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une livraison")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Long id) {
        livraisonService.deleteLivraison(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/livraisons/dates?debut=2024-01-01&fin=2024-12-31
    @GetMapping("/dates")
    @Operation(summary = "Livraisons entre deux dates")
    public ResponseEntity<List<LivraisonDTO>> getByDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(livraisonService.getLivraisonsBetweenDates(debut, fin));
    }

    // GET /api/livraisons/ville?ville=Paris
    @GetMapping("/ville")
    @Operation(summary = "Livraisons par ville de destination")
    public ResponseEntity<List<LivraisonDTO>> getByVille(@RequestParam String ville) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByVille(ville));
    }

    // GET /api/livraisons/client/1
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Livraisons par client")
    public ResponseEntity<List<LivraisonDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByClient(clientId));
    }

    // GET /api/livraisons/statut?statut=ENCOURS
    @GetMapping("/statut")
    @Operation(summary = "Livraisons par statut")
    public ResponseEntity<List<LivraisonDTO>> getByStatut(@RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByStatut(statut));
    }
}
