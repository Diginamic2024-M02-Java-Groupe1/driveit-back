package com.driveit.driveit;

import com.driveit.driveit.address.AddressRepository;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.brand.BrandRepository;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryRepository;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import com.driveit.driveit.country.CountryRepository;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.model.ModelRepository;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationRepository;
import com.driveit.driveit.vehicle.StatusVehicle;
import com.driveit.driveit.vehicle.Vehicle;
import com.driveit.driveit.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Fixture {


    private final VehicleRepository vehicleRepository;

    private final BrandRepository brandRepository;

    private final CollaboratorRepository collaboratorRepository;

    private final ModelRepository modelRepository;

    private final CategoryRepository categoryRepository;

    private final CountryRepository countryRepository;

    private final AddressRepository addressRepository;

    private final MotorizationRepository motorizationRepository;

    @Autowired
    public Fixture(VehicleRepository vehicleRepository, BrandRepository brandRepository, CollaboratorRepository collaboratorRepository, ModelRepository modelRepository, CategoryRepository categoryRepository, CountryRepository countryRepository, AddressRepository addressRepository, MotorizationRepository motorizationRepository) {
        this.vehicleRepository = vehicleRepository;
        this.brandRepository = brandRepository;
        this.collaboratorRepository = collaboratorRepository;
        this.modelRepository = modelRepository;
        this.categoryRepository = categoryRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.motorizationRepository = motorizationRepository;
    }

    public boolean loadCollaborator(CollaboratorRepository collaboratorRepository) {
        boolean flag = false;
        for (int i = 0; i < 10; i++) {
            collaboratorRepository.save(new Collaborator("Collaborator" + i, "Collaborator" + i, flag ? "ROLE_ADMIN" : "ROLE_USER"));
            flag = !flag;
        }
        return true;
    }

//    public boolean loadCountryAndAddress(CountryRepository countryRepository, AddressRepository addressRepository) {
//        List<Country> countries = List.of(
//            new Country("France"),
//            new Country("Allemagne"),
//            new Country("Italie"),
//            new Country("Espagne"),
//            new Country("Portugal"),
//            new Country("Belgique")
//        );
//        List<Address> addresses = List.of(
//                new Address("1", "rue de Paris", "Paris", 75000, countries.get(0)),
//                new Address("2", "rue de Berlin", "Berlin", 10000, countries.get(1)),
//                new Address("3", "rue de Rome", "Rome", 20000, countries.get(2)),
//                new Address("4", "rue de Madrid", "Madrid", 30000, countries.get(3)),
//                new Address("5", "rue de Lisbonne", "Lisbonne", 40000, countries.get(4)),
//                new Address("6", "rue de Bruxelles", "Bruxelles", 50000, countries.get(5))
//        );
//        countryRepository.saveAll(countries);
//        addressRepository.saveAll(addresses);
//        return true;
//    }

    public boolean loadVehicle() {
        List<Motorization> motorizations = List.of(
                new Motorization("Diesel"),
                new Motorization("Essence"),
                new Motorization("Hybride"),
                new Motorization("Elect")
        );
        List<Brand> brands = List.of(
            new Brand("Renault"),
            new Brand("Peugeot"),
            new Brand("Citroen"),
            new Brand("Volkswagen")
        );
        List<Model> models = List.of(
                new Model("Clio", brands.get(0)),
                new Model("Megane", brands.get(0)),
                new Model("208", brands.get(1)),
                new Model("308", brands.get(1)),
                new Model("C3", brands.get(2)),
                new Model("C4", brands.get(2)),
                new Model("Golf", brands.get(3)),
                new Model("Polo", brands.get(3))
        );
        List<Category> categories = List.of(
                new Category("Citadine"),
                new Category("Berline"),
                new Category("SUV"),
                new Category("Utilitaire")
        );
        List<Vehicle> vehicles = List.of(
                new Vehicle("AB-123-CD", 3, false, "img.png", 3211, StatusVehicle.AVAILABLE , motorizations.get(0), models.get(0), categories.get(0)),
                new Vehicle("EF-456-GH", 4, false, "img.png", 3211, StatusVehicle.AVAILABLE , motorizations.get(1), models.get(1), categories.get(1)),
                new Vehicle("IJ-789-KL", 3, false, "img.png", 3211, StatusVehicle.AVAILABLE , motorizations.get(2), models.get(2), categories.get(2)),
                new Vehicle("MN-101-OP", 4, false, "img.png", 3211, StatusVehicle.AVAILABLE , motorizations.get(3), models.get(3), categories.get(3)),
                new Vehicle("QR-112-ST", 3, false, "img.png", 3211, StatusVehicle.AVAILABLE , motorizations.get(0), models.get(4), categories.get(0))
        );
        motorizationRepository.saveAll(motorizations);
        System.out.println("Motorizations loaded");
        categoryRepository.saveAll(categories);
        System.out.println("Categories loaded");
        brandRepository.saveAll(brands);
        System.out.println("Brands loaded");
        modelRepository.saveAll(models);
        System.out.println("Models loaded");
        vehicleRepository.saveAll(vehicles);
        System.out.println("Vehicles loaded");
        return true;
    }


//    public boolean load() {
//        loadCollaborator(collaboratorRepository);
//        System.out.println("Collaborators loaded");
//        loadCountryAndAddress(countryRepository, addressRepository);
//        System.out.println("Countries and Addresses loaded");
//        loadVehicle();
//        System.out.println("Vehicles loaded");
//        return true;
//    }



}
