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
        // Suppression des anciennes données
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
        CityZipCode city3 = new CityZipCode("Marseille", "13001");
        cityZipCodeRepository.save(city1);
        cityZipCodeRepository.save(city2);
        cityZipCodeRepository.save(city3);

        // Address
        Address address1 = new Address("10", "Rue de Rivoli", city1);
        Address address2 = new Address("20", "Rue de la République", city2);
        Address address3 = new Address("5", "Boulevard Longchamp", city3);
        Address address4 = new Address("15", "Avenue de l'Opéra", city1);
        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);
        addressRepository.save(address4);

        // Category, Motorization, Brand, Model, Vehicle
        Category category = new Category("Citadine");
        categoryRepository.save(category);
        Motorization motorization = new Motorization("Essence");
        motorizationRepository.save(motorization);
        Brand brand = new Brand("Renault");
        brandRepository.save(brand);
        Model model = new Model("Clio", brand);
        modelRepository.save(model);
        Vehicle vehicle = new Vehicle("AB-123-CD", 5, true, "https://img.com/vehicule.jpg", 120.0, motorization, model, category);
        vehicle.setStatus(StatusVehicle.AVAILABLE);
        vehicleRepository.save(vehicle);

        // Collaborators
        String rawPassword = "admin";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Collaborator collab1 = new Collaborator("jean.dupont@email.com", encodedPassword, "Jean", "Dupont");
        Collaborator collab2 = new Collaborator("marie.curie@email.com", encodedPassword, "Marie", "Curie");
        Collaborator collab3 = new Collaborator("paul.durand@email.com", encodedPassword, "Paul", "Durand");
        collaboratorRepository.save(collab1);
        collaboratorRepository.save(collab2);
        collaboratorRepository.save(collab3);

        // Carpoolings
        Carpooling carpooling1 = new Carpooling(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                collab1, address1, address2, Collections.emptyList(), vehicle
        );
        Carpooling carpooling2 = new Carpooling(
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(3),
                collab2, address2, address3, Collections.emptyList(), vehicle
        );
        Carpooling carpooling3 = new Carpooling(
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(1),
                collab3, address3, address4, Collections.emptyList(), vehicle
        );
        carpoolingRepository.save(carpooling1);
        carpoolingRepository.save(carpooling2);
        carpoolingRepository.save(carpooling3);

        // Reservations : collab1 participe à son propre covoiturage, collab2 à un autre, collab3 à aucun
        ReservationCarpooling reservation1 = new ReservationCarpooling(carpooling1, collab1, StatusReservationCarpooling.ACCEPTED);
        ReservationCarpooling reservation2 = new ReservationCarpooling(carpooling2, collab1, StatusReservationCarpooling.PENDING); // collab1 n'est que passager ici
        reservationCarpoolingRepository.save(reservation1);
        reservationCarpoolingRepository.save(reservation2);
        // carpooling3 : aucun participant pour collab1 (cas "je ne suis pas encore présent")
    }
}

