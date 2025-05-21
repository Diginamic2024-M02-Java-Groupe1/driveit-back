package com.driveit.driveit._utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.address.AddressRepository;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.brand.BrandRepository;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.carpooling.CarpoolingRepository;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryRepository;
import com.driveit.driveit.cityzipcode.CityZipCode;
import com.driveit.driveit.cityzipcode.CityZipcodeRepository;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.model.ModelRepository;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationRepository;
import com.driveit.driveit.reservationcarpooling.ReservationCarpooling;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingRepository;
import com.driveit.driveit.reservationcarpooling.StatusReservationCarpooling;
import com.driveit.driveit.vehicle.StatusVehicle;
import com.driveit.driveit.vehicle.Vehicle;
import com.driveit.driveit.vehicle.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired private CityZipcodeRepository cityZipCodeRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private MotorizationRepository motorizationRepository;
    @Autowired private BrandRepository brandRepository;
    @Autowired private ModelRepository modelRepository;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private CollaboratorRepository collaboratorRepository;
    @Autowired private CarpoolingRepository carpoolingRepository;
    @Autowired private ReservationCarpoolingRepository reservationCarpoolingRepository;
    @Autowired private com.driveit.driveit._auth.RefreshTokenRepository refreshTokenRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Suppression des anciennes données (ordre important à respecter pour les contraintes de clé étrangère)
        refreshTokenRepository.deleteAll();
        reservationCarpoolingRepository.deleteAll();
        carpoolingRepository.deleteAll();
        collaboratorRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        motorizationRepository.deleteAll();
        categoryRepository.deleteAll();
        addressRepository.deleteAll();
        cityZipCodeRepository.deleteAll();

        // CityZipCode
        CityZipCode city1 = new CityZipCode("Paris", "75001");
        CityZipCode city2 = new CityZipCode("Lyon", "69001");
        cityZipCodeRepository.save(city1);
        cityZipCodeRepository.save(city2);

        // Address
        Address address1 = new Address("10", "Rue de Rivoli", city1);
        Address address2 = new Address("20", "Rue de la République", city2);
        addressRepository.save(address1);
        addressRepository.save(address2);

        // Category
        Category category = new Category("Citadine");
        categoryRepository.save(category);

        // Motorization
        Motorization motorization = new Motorization("Essence");
        motorizationRepository.save(motorization);

        // Brand & Model
        Brand brand = new Brand("Renault");
        brandRepository.save(brand);
        Model model = new Model("Clio", brand);
        modelRepository.save(model);

        // Vehicle
        Vehicle vehicle = new Vehicle("AB-123-CD", 5, true, "https://img.com/vehicule.jpg", 120.0, motorization, model, category);
        vehicle.setStatus(StatusVehicle.AVAILABLE);
        vehicleRepository.save(vehicle);

        // Collaborator
        String rawPassword = "admin";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Collaborator collaborator = new Collaborator( "jean.dupont@email.com",encodedPassword, "Jean", "Dupont");
        collaboratorRepository.save(collaborator);

        // Carpooling
        Carpooling carpooling = new Carpooling(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                collaborator,
                address1,
                address2,
                Collections.emptyList(),
                vehicle
        );
        carpoolingRepository.save(carpooling);

        // ReservationCarpooling
        ReservationCarpooling reservation = new ReservationCarpooling(carpooling, collaborator, StatusReservationCarpooling.ACCEPTED);
        reservationCarpoolingRepository.save(reservation);
    }
}

