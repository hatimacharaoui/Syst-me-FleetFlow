package com.fleetflow.Controller;

📄 LivraisonController.java — Couche Web (REST)
        java// src/main/java/com/fleetflow/controller/LivraisonController.java
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

/**
 * @RestController  → Dit à Spring que cette classe est un contrôleur REST.
 *                   Chaque méthode retourne directement du JSON (pas de vue HTML).
 *
 * @RequestMapping("/api/livraisons") → Toutes les URLs de ce contrôleur
 *                   commencent par /api/livraisons
 *
 * @RequiredArgsConstructor → Lombok génère automatiquement un constructeur
 *                   avec tous les champs "final". Évite d'écrire @Autowired.
 *
 * @Tag → Annotation Swagger : regroupe ces endpoints sous le titre
 *         "Livraisons" dans la documentation interactive (Swagger UI).
 */
@RestController
@RequestMapping("/api/livraisons")
@RequiredArgsConstructor
@Tag(name = "Livraisons", description = "Gestion des livraisons")
public class LivraisonController {

    // Spring injecte automatiquement le service grâce à @RequiredArgsConstructor
    // Le contrôleur ne fait PAS de logique métier, il délègue tout au service
    private final LivraisonService livraisonService;

    /**
     * GET /api/livraisons
     * → Retourne la liste de toutes les livraisons en base
     * ResponseEntity<List<LivraisonDTO>> = réponse HTTP avec un corps de type liste
     */
    @GetMapping
    @Operation(summary = "Lister toutes les livraisons")
    public ResponseEntity<List<LivraisonDTO>> getAllLivraisons() {
        // .ok(...) = code HTTP 200 OK + corps JSON
        return ResponseEntity.ok(livraisonService.getAllLivraisons());
    }

    /**
     * GET /api/livraisons/{id}
     * → Retourne UNE livraison dont l'id est passé dans l'URL
     * Exemple : GET /api/livraisons/5
     * @PathVariable Long id → Spring extrait "5" de l'URL et le met dans "id"
     */
    @GetMapping("/{id}")
    @Operation(summary = "Trouver une livraison par ID")
    public ResponseEntity<LivraisonDTO> getLivraisonById(@PathVariable Long id) {
        return ResponseEntity.ok(livraisonService.getLivraisonById(id));
    }

    /**
     * POST /api/livraisons
     * → Crée une nouvelle livraison
     * @RequestBody LivraisonDTO → Spring lit le JSON du corps de la requête
     *              et le convertit automatiquement en objet LivraisonDTO
     * HttpStatus.CREATED = code HTTP 201 (ressource créée avec succès)
     */
    @PostMapping
    @Operation(summary = "Créer une livraison")
    public ResponseEntity<LivraisonDTO> createLivraison(@RequestBody LivraisonDTO livraisonDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livraisonService.createLivraison(livraisonDTO));
    }

    /**
     * PUT /api/livraisons/{id}/assigner?chauffeurId=2&vehiculeId=3
     * → Assigne un chauffeur ET un véhicule à une livraison existante
     *
     * @PathVariable Long id       → id de la livraison dans l'URL
     * @RequestParam Long chauffeurId → paramètre dans l'URL après "?"
     * @RequestParam Long vehiculeId  → deuxième paramètre après "&"
     *
     * Exemple d'appel : PUT /api/livraisons/1/assigner?chauffeurId=2&vehiculeId=3
     */
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

    /**
     * PUT /api/livraisons/{id}/statut?statut=LIVREE
     * → Change le statut d'une livraison (ex: ENATTENTE → ENCOURS → LIVREE)
     *
     * @RequestParam StatutLivraison statut → Spring convertit automatiquement
     *              la chaîne "LIVREE" en valeur de l'enum StatutLivraison
     */
    @PutMapping("/{id}/statut")
    @Operation(summary = "Modifier le statut d'une livraison")
    public ResponseEntity<LivraisonDTO> modifierStatut(
            @PathVariable Long id,
            @RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.modifierStatut(id, statut));
    }

    /**
     * DELETE /api/livraisons/{id}
     * → Supprime une livraison par son id
     * ResponseEntity<Void> = réponse sans corps (juste un code HTTP)
     * .noContent().build() = code HTTP 204 No Content (suppression réussie)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une livraison")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Long id) {
        livraisonService.deleteLivraison(id);
        return ResponseEntity.noContent().build(); // 204
    }

    /**
     * GET /api/livraisons/dates?debut=2024-01-01&fin=2024-12-31
     * → Retourne les livraisons planifiées entre deux dates
     *
     * @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) → indique à Spring
     *   que le paramètre est au format ISO "yyyy-MM-dd" (ex: 2024-01-01)
     *   Sans cette annotation, Spring ne saurait pas comment parser la date.
     */
    @GetMapping("/dates")
    @Operation(summary = "Livraisons entre deux dates")
    public ResponseEntity<List<LivraisonDTO>> getByDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(livraisonService.getLivraisonsBetweenDates(debut, fin));
    }

    /**
     * GET /api/livraisons/ville?ville=Paris
     * → Retourne toutes les livraisons dont la ville de destination est "Paris"
     */
    @GetMapping("/ville")
    @Operation(summary = "Livraisons par ville de destination")
    public ResponseEntity<List<LivraisonDTO>> getByVille(@RequestParam String ville) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByVille(ville));
    }

    /**
     * GET /api/livraisons/client/1
     * → Retourne toutes les livraisons appartenant au client avec l'id 1
     * @PathVariable (pas @RequestParam) car l'id est dans le chemin de l'URL
     */
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Livraisons par client")
    public ResponseEntity<List<LivraisonDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByClient(clientId));
    }

    /**
     * GET /api/livraisons/statut?statut=ENCOURS
     * → Retourne toutes les livraisons ayant le statut ENCOURS
     * Spring convertit automatiquement la chaîne en valeur d'enum
     */
    @GetMapping("/statut")
    @Operation(summary = "Livraisons par statut")
    public ResponseEntity<List<LivraisonDTO>> getByStatut(@RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByStatut(statut));
    }
}

📄 LivraisonService.java — Couche Métier (Business Logic)
java// src/main/java/com/fleetflow/service/LivraisonService.java
package com.fleetflow.service;

import ...

/**
 * @Service → Dit à Spring que c'est un bean de service (logique métier).
 *            Spring le crée et le gère automatiquement.
 *
 * @RequiredArgsConstructor → Lombok génère le constructeur avec tous les
 *            champs "final" → injection automatique sans @Autowired.
 *
 * RÔLE DE CETTE CLASSE :
 *   - Contient TOUTE la logique métier (règles, validations, orchestration)
 *   - Fait le lien entre le Controller (HTTP) et les Repositories (BDD)
 *   - Utilise le LivraisonMapper pour convertir Entity ↔ DTO
 */
@Service
@RequiredArgsConstructor
public class LivraisonService {

    private final LivraisonRepository livraisonRepository; // accès BDD livraisons
    private final LivraisonMapper livraisonMapper;          // convertisseur Entity ↔ DTO
    private final ChauffeurRepository chauffeurRepository;  // accès BDD chauffeurs
    private final VehiculeRepository vehiculeRepository;    // accès BDD véhicules

    /**
     * Retourne toutes les livraisons sous forme de DTO.
     *
     * findAll()           → récupère toutes les entités Livraison depuis la BDD
     * .stream()           → convertit la liste en flux (pour pouvoir traiter chaque élément)
     * .map(livraisonMapper::toDTO) → transforme chaque entité Livraison en LivraisonDTO
     *                               (:: est une référence de méthode Java, équivalent à
     *                                livraison -> livraisonMapper.toDTO(livraison))
     * .collect(Collectors.toList()) → rassemble les résultats dans une nouvelle liste
     */
    public List<LivraisonDTO> getAllLivraisons() {
        return livraisonRepository.findAll()
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne UNE livraison par son id.
     *
     * findById(id)   → retourne un Optional<Livraison> (peut être vide si id inexistant)
     * .orElseThrow() → si l'Optional est vide, lance une exception RuntimeException
     *                  avec un message explicite → sera attrapée et renvoyée en HTTP 500
     *                  (idéalement on utiliserait une exception personnalisée + @ControllerAdvice)
     */
    public LivraisonDTO getLivraisonById(Long id) {
        Livraison livraison = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée avec l'id: " + id));
        return livraisonMapper.toDTO(livraison);
    }

    /**
     * Crée une nouvelle livraison en base de données.
     *
     * Étape 1 : livraisonMapper.toEntity(livraisonDTO)
     *           → Convertit le DTO (reçu du client JSON) en entité JPA
     *
     * Étape 2 : livraison.setStatut(StatutLivraison.ENATTENTE)
     *           → Force le statut initial à ENATTENTE,
     *             peu importe ce que le client a envoyé dans le JSON
     *             (règle métier : toute nouvelle livraison commence en attente)
     *
     * Étape 3 : livraisonRepository.save(livraison)
     *           → Sauvegarde en BDD et retourne l'entité avec l'id généré
     *
     * Étape 4 : livraisonMapper.toDTO(...)
     *           → Reconvertit l'entité sauvegardée en DTO pour la réponse
     */
    public LivraisonDTO createLivraison(LivraisonDTO livraisonDTO) {
        Livraison livraison = livraisonMapper.toEntity(livraisonDTO);
        livraison.setStatut(StatutLivraison.ENATTENTE); // règle métier
        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }

    /**
     * Assigne un chauffeur ET un véhicule à une livraison existante.
     * C'est la méthode la plus complexe du service : elle orchestre
     * plusieurs entités et applique des règles métier.
     *
     * RÈGLES MÉTIER APPLIQUÉES :
     *  - Le chauffeur doit être disponible (disponible == true)
     *  - Le véhicule doit avoir le statut DISPONIBLE
     *  - Après assignation : la livraison passe en ENCOURS,
     *    le chauffeur devient indisponible, le véhicule passe EN_LIVRAISON
     */
    public LivraisonDTO assignerChauffeurEtVehicule(Long livraisonId,
                                                    Long chauffeurId,
                                                    Long vehiculeId) {
        // ÉTAPE 1 : Récupère la livraison (exception si inexistante)
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));

        // ÉTAPE 2 : Récupère le chauffeur (exception si inexistant)
        Chauffeur chauffeur = chauffeurRepository.findById(chauffeurId)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé"));

        // ÉTAPE 3 : Récupère le véhicule (exception si inexistant)
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        // ÉTAPE 4 : Vérifie la disponibilité du chauffeur
        // Si le chauffeur est déjà en mission, on lève une exception
        if (!chauffeur.getDisponible()) {
            throw new RuntimeException("Le chauffeur n'est pas disponible");
        }

        // ÉTAPE 5 : Vérifie la disponibilité du véhicule
        // != compare des valeurs d'enum → DISPONIBLE signifie prêt à l'emploi
        if (vehicule.getStatut() != StatutVehicule.DISPONIBLE) {
            throw new RuntimeException("Le véhicule n'est pas disponible");
        }

        // ÉTAPE 6 : Effectue l'assignation (toutes les vérifications sont passées)
        livraison.setChauffeur(chauffeur);   // lie le chauffeur à la livraison
        livraison.setVehicule(vehicule);     // lie le véhicule à la livraison
        livraison.setStatut(StatutLivraison.ENCOURS); // la livraison démarre

        // ÉTAPE 7 : Met à jour les statuts du chauffeur et du véhicule
        chauffeur.setDisponible(false);                   // chauffeur occupé
        vehicule.setStatut(StatutVehicule.EN_LIVRAISON);  // véhicule en route

        // ÉTAPE 8 : Sauvegarde les modifications en BDD
        // On doit sauvegarder CHAQUE entité modifiée séparément
        chauffeurRepository.save(chauffeur);
        vehiculeRepository.save(vehicule);

        // Sauvegarde la livraison et retourne le DTO final
        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }

    /**
     * Modifie le statut d'une livraison.
     *
     * CAS SPÉCIAL : si le nouveau statut est LIVREE (livraison terminée),
     * on libère automatiquement le chauffeur et le véhicule.
     *
     * livraison.getChauffeur() != null → vérifie qu'un chauffeur était bien assigné
     *   (une livraison peut ne pas encore avoir de chauffeur assigné)
     */
    public LivraisonDTO modifierStatut(Long id, StatutLivraison nouveauStatut) {
        Livraison livraison = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));

        livraison.setStatut(nouveauStatut);

        // Règle métier : libération automatique à la fin de la livraison
        if (nouveauStatut == StatutLivraison.LIVREE) {

            // Libère le chauffeur (redevient disponible pour d'autres missions)
            if (livraison.getChauffeur() != null) {
                livraison.getChauffeur().setDisponible(true);
                chauffeurRepository.save(livraison.getChauffeur());
            }

            // Remet le véhicule en statut DISPONIBLE
            if (livraison.getVehicule() != null) {
                livraison.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
                vehiculeRepository.save(livraison.getVehicule());
            }
        }

        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }

    /**
     * Supprime une livraison par son id.
     *
     * existsById(id) → vérifie d'abord l'existence AVANT de supprimer
     *   → meilleure pratique que deleteById() direct qui ne lève pas d'exception
     *     si l'id est inexistant (silencieux)
     */
    public void deleteLivraison(Long id) {
        if (!livraisonRepository.existsById(id)) {
            throw new RuntimeException("Livraison non trouvée avec l'id: " + id);
        }
        livraisonRepository.deleteById(id);
    }

    /**
     * Retourne les livraisons entre deux dates (ex: 1er janvier au 31 décembre).
     * findLivraisonsBetweenDates() est une méthode personnalisée définie dans
     * LivraisonRepository avec une @Query JPQL ou une dérivation de nom.
     */
    public List<LivraisonDTO> getLivraisonsBetweenDates(LocalDate debut, LocalDate fin) {
        return livraisonRepository.findLivraisonsBetweenDates(debut, fin)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne toutes les livraisons vers une ville précise.
     * findByVilleDestination(ville) → méthode dérivée Spring Data JPA
     *   (Spring génère automatiquement la requête SQL : WHERE ville_destination = ?)
     */
    public List<LivraisonDTO> getLivraisonsByVille(String ville) {
        return livraisonRepository.findByVilleDestination(ville)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne toutes les livraisons d'un client donné (par son id).
     * findByClientId(clientId) → Spring Data JPA traduit en :
     *   SELECT * FROM livraison WHERE client_id = ?
     */
    public List<LivraisonDTO> getLivraisonsByClient(Long clientId) {
        return livraisonRepository.findByClientId(clientId)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne toutes les livraisons ayant un statut précis.
     * findByStatut(statut) → Spring Data JPA traduit en :
     *   SELECT * FROM livraison WHERE statut = ?
     */
    public List<LivraisonDTO> getLivraisonsByStatut(StatutLivraison statut) {
        return livraisonRepository.findByStatut(statut)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }
}

🧠 Résumé visuel du flux d'une requête
Client HTTP (Postman/Frontend)
        ↓  JSON
LivraisonController       ← reçoit la requête, valide le format HTTP
        ↓  appelle
LivraisonService          ← applique les règles métier
        ↓  utilise
Repository (JPA)          ← lit/écrit en base de données
        ↓  retourne entité
LivraisonMapper           ← convertit Entity → DTO
        ↓  DTO
LivraisonController       ← envoie la réponse JSON au client
Le point clé à retenir : le Controller ne fait que recevoir/répondre, le Service contient toute la logique (vérifications de disponibilité, changements de statut, libération des ressources).
