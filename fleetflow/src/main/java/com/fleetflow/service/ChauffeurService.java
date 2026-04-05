package com.fleetflow.Service;

import com.fleetflow.Dto.ChauffeurDTO;
import com.fleetflow.Entity.Chauffeur;
import com.fleetflow.Mapper.ChauffeurMapper;
import com.fleetflow.Repository.ChauffeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;
    private final ChauffeurMapper chauffeurMapper;

    public List<ChauffeurDTO> getAllChauffeurs() {
         List<Chauffeur> chauffeurs = chauffeurRepository.findAll();
         return chauffeurMapper.toDTOs(chauffeurs);
    }


    public ChauffeurDTO getChauffeurById(Long id) {
        Chauffeur chauffeur = chauffeurRepository.findById(id).orElseThrow();
        return chauffeurMapper.toDTO(chauffeur);
    }

    public ChauffeurDTO createChauffeur(ChauffeurDTO dto) {
        Chauffeur chauffeur = chauffeurMapper.toEntity(dto);
        chauffeur = chauffeurRepository.save(chauffeur);
        return chauffeurMapper.toDTO(chauffeur);
    }

    public ChauffeurDTO updateChauffeur(Long id, ChauffeurDTO dto) {
        Chauffeur chauffeur = chauffeurRepository.findById(id)
                .orElseThrow();

        chauffeur.setNom(dto.getNom());
        chauffeur.setTelephone(dto.getTelephone());
        chauffeur.setPermisType(dto.getPermisType());
        chauffeur.setDisponible(dto.getDisponible());

        return chauffeurMapper.toDTO(chauffeurRepository.save(chauffeur));
    }

    public void deleteChauffeur(Long id) {
        if (!chauffeurRepository.existsById(id)) {
            throw new RuntimeException("Chauffeur non trouvé");
        }
        chauffeurRepository.deleteById(id);
    }

    public List<ChauffeurDTO> getChauffeursdisponibles() {
         List<Chauffeur> chauffeurs = chauffeurRepository.findByDisponibleTrue();
         return chauffeurMapper.toDTOs(chauffeurs);
    }



}