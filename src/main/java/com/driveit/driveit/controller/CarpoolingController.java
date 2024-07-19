package com.driveit.driveit.controller;

import com.driveit.driveit.Fixture;
import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.utils.Mapper;
import com.driveit.driveit.carpooling.CarpoolingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carpooling")
public class CarpoolingController {

    @Autowired
    private CarpoolingService carpoolingService;

    @Autowired
    private Fixture fixture;


    @GetMapping("/fixture")
    public ResponseEntity<String> fixture() {
        boolean carpooling = fixture.load();
        return ResponseEntity.ok("it's okay");
    }

//    // Obtenir tous les covoiturages
//    @GetMapping("/all")
//    public ResponseEntity<CarpoolingDto> getCarpoolings() {
//        CarpoolingDto carpoolingDto = Mapper.carpoolingToDto(carpoolingService.getAllCarpoolings());
//        return ResponseEntity.ok(carpoolingDto);
//    }

    // Créer un nouveau covoiturage
    @PostMapping("/create")
    public ResponseEntity<Carpooling> createCarpooling(@RequestBody Carpooling carpooling) {
        Carpooling createdCarpooling = carpoolingService.save(carpooling);
        return ResponseEntity.ok(createdCarpooling);
    }

    // Obtenir un covoiturage par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Carpooling> getCarpoolingById(@PathVariable int id) {
        Carpooling carpooling = carpoolingService.getCarpoolingById(id);
        return ResponseEntity.ok(carpooling);
    }

}