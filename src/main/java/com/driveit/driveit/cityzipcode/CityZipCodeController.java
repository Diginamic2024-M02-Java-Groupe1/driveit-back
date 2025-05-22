package com.driveit.driveit.cityzipcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/city-zipcode")
public class CityZipCodeController {

    private final CityZipCodeService cityZipCodeService;

    @Autowired
    public CityZipCodeController(CityZipCodeService cityZipCodeService) {
        this.cityZipCodeService = cityZipCodeService;
    }

    @GetMapping("/getCities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(cityZipCodeService.getAllCities());
    }

}
