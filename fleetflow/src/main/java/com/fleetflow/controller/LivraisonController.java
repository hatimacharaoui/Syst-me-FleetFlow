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

    @GetMapping("/dates")
    @Operation(summary = "Livraisons entre deux dates")
    public ResponseEntity<List<LivraisonDTO>> getByDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(livraisonService.getLivraisonsBetweenDates(debut, fin));
    }

    @GetMapping("/ville")
    @Operation(summary = "Livraisons par ville de destination")
    public ResponseEntity<List<LivraisonDTO>> getByVille(@RequestParam String ville) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByVille(ville));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Livraisons par client")
    public ResponseEntity<List<LivraisonDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByClient(clientId));
    }

    @GetMapping("/statut")
    @Operation(summary = "Livraisons par statut")
    public ResponseEntity<List<LivraisonDTO>> getByStatut(@RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByStatut(statut));
    }
}