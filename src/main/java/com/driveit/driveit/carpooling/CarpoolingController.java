package com.driveit.driveit.carpooling;


import com.driveit.driveit.reservationcarpooling.StatusReservationCarpooling;
import com.driveit.driveit.reservationvehicle.StatusFilter;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/api/carpooling")
public class CarpoolingController {

    private final CarpoolingService carpoolingService;


    @Autowired
    public CarpoolingController(
            CarpoolingService carpoolingService
    ) {
        this.carpoolingService = carpoolingService;
    }

    /**
     * Avoir la liste des covoiturages dont un utilisateur est organisateur
     *
     * @param id l'id de l'organisateur
     *
     */
    @GetMapping("/organizer/{id}")
    public List<CarpoolingDto> getCarpoolings(@PathVariable int id) {
        return carpoolingService.getCarpoolingsByOrganizer(id);
    }

    /**
     * Ajouter un covoiturage
     *
     * @param newCarpoolingDto les informations du covoiturage à ajouter
     *
     */
    @PostMapping("")
    public String insertCarpooling(@RequestBody BodyCarpoolingDto newCarpoolingDto) {
        carpoolingService.insert(newCarpoolingDto);
        return "Carpooling inserted";
    }

    /**
     * Mettre à jour un covoiturage
     *
     * @param id l'id du covoiturage à mettre à jour
     * @param carpoolingDto les informations du covoiturage à mettre à jour
     *
     */
    @PutMapping("/{id}")
    public String updateCarpooling(@PathVariable int id, @RequestBody BodyCarpoolingDto carpoolingDto) {
        carpoolingService.update(id, carpoolingDto);
        return "Carpooling updated";
    }

    /**
     * Supprimer un covoiturage
     *
     * @param id l'id du covoiturage à supprimer
     *
     */
    @DeleteMapping("/{id}")
    public String deleteCarpooling(@PathVariable int id) {
        carpoolingService.delete(id);
        return "Carpooling deleted";
    }

    /**
     * Retourner un covoiturage selon son id
     * @param id l'id du covoiturage à retourner
     */
    @GetMapping("/{id}")
    public CarpoolingDto getCarpooling(@PathVariable int id) {
        return carpoolingService.getCarpoolingById(id);
    }

    /**
     * Ajoute un participant à un covoiturage
     * @param id l'id du covoiturage
     * @param idParticipant l'id du participant
     */
    @GetMapping("/{id}/participants/{idParticipant}")
    public String addPassenger(@PathVariable int id, @PathVariable int idParticipant) {
        carpoolingService.addParticipant(id, idParticipant);
        return "Passenger added";
    }


    /**
     * Mettre à jour le statut d'un participant à un covoiturage
     * @param id l'id du covoiturage
     * @param idParticipant l'id du participant
     * @param status le statut du participant
     */
    @PutMapping("/{id}/participants/{idParticipant}")
    public String updatePassenger(@PathVariable int id, @PathVariable int idParticipant,  @RequestParam StatusReservationCarpooling status) {
        StatusReservationCarpooling statusReservationCarpooling = switch (status) {
            case ACCEPTED -> {
                carpoolingService.acceptParticipant(id, idParticipant);
                yield StatusReservationCarpooling.ACCEPTED;
            }
            case REFUSED -> {
                carpoolingService.refuseParticipant(id, idParticipant);
                yield StatusReservationCarpooling.REFUSED;
            }
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
        return "Passenger " + statusReservationCarpooling;
    }




}
