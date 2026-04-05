package com.fleetflow.Controller;

import com.fleetflow.Dto.ChauffeurDTO;
import com.fleetflow.Service.ChauffeurService;
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