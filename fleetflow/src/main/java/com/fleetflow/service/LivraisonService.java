package com.fleetflow.Service;

import com.fleetflow.Dto.LivraisonDTO;
import com.fleetflow.Entity.Chauffeur;
import com.fleetflow.Entity.Livraison;
import com.fleetflow.Entity.Vehicule;
import com.fleetflow.Enums.StatutLivraison;
import com.fleetflow.Enums.StatutVehicule;
import com.fleetflow.Mapper.LivraisonMapper;
import com.fleetflow.Repository.ChauffeurRepository;
import com.fleetflow.Repository.LivraisonRepository;
import com.fleetflow.Repository.VehiculeRepository;
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

    public LivraisonDTO createLivraison(LivraisonDTO livraisonDTO) {
        Livraison livraison = livraisonMapper.toEntity(livraisonDTO);
        livraison.setStatut(StatutLivraison.ENATTENTE); // Statut initial
        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }

    public LivraisonDTO assignerChauffeurEtVehicule(Long livraisonId,
                                                    Long chauffeurId,
                                                    Long vehiculeId) {

        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));


        Chauffeur chauffeur = chauffeurRepository.findById(chauffeurId)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé"));


        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));


        if (!chauffeur.getDisponible()) {
            throw new RuntimeException("Le chauffeur n'est pas disponible");
        }
        if (vehicule.getStatut() != StatutVehicule.DISPONIBLE) {
            throw new RuntimeException("Le véhicule n'est pas disponible");
        }


        livraison.setChauffeur(chauffeur);
        livraison.setVehicule(vehicule);
        livraison.setStatut(StatutLivraison.ENCOURS);

        chauffeur.setDisponible(false);
        vehicule.setStatut(StatutVehicule.EN_LIVRAISON);


        chauffeurRepository.save(chauffeur);
        vehiculeRepository.save(vehicule);

        return livraisonMapper.toDTO(livraisonRepository.save(livraison));
    }


    public LivraisonDTO modifierStatut(Long id, StatutLivraison nouveauStatut) {
        Livraison livraison = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));

        livraison.setStatut(nouveauStatut);


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


    public List<LivraisonDTO> getLivraisonsBetweenDates(LocalDate debut, LocalDate fin) {
        return livraisonRepository.findLivraisonsBetweenDates(debut, fin)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<LivraisonDTO> getLivraisonsByVille(String ville) {
        return livraisonRepository.findByVilleDestination(ville)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LivraisonDTO> getLivraisonsByClient(Long clientId) {
        return livraisonRepository.findByClientId(clientId)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<LivraisonDTO> getLivraisonsByStatut(StatutLivraison statut) {
        return livraisonRepository.findByStatut(statut)
                .stream()
                .map(livraisonMapper::toDTO)
                .collect(Collectors.toList());
    }
}