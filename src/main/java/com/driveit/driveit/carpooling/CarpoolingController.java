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


    @GetMapping("")
    public List<CarpoolingDto> getCarpoolings() {
        return carpoolingService.getCarpoolings();
    }

    @PostMapping("")
    public String insertCarpooling(@RequestBody BodyCarpoolingDto newCarpoolingDto) {
        carpoolingService.insert(newCarpoolingDto);
        return "Carpooling inserted";
    }

    @PutMapping("/{id}")
    public String updateCarpooling(@PathVariable int id, @RequestBody BodyCarpoolingDto carpoolingDto) {
        carpoolingService.update(id, carpoolingDto);
        return "Carpooling updated";
    }

    @DeleteMapping("/{id}")
    public String deleteCarpooling(@PathVariable int id) {
        carpoolingService.delete(id);
        return "Carpooling deleted";
    }

    @GetMapping("/{id}")
    public CarpoolingDto getCarpooling(@PathVariable int id) {
        return carpoolingService.getCarpoolingById(id);
    }

    @GetMapping("/{id}/participants/{idParticipant}")
    public String addPassenger(@PathVariable int id, @PathVariable int idParticipant) {
        carpoolingService.addParticipant(id, idParticipant);
        return "Passenger added";
    }

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
