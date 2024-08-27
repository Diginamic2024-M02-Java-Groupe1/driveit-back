package com.driveit.driveit.vehicle;

import com.driveit.driveit._utils.Response;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.brand.BrandRepository;
import com.driveit.driveit.brand.BrandService;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryRepository;
import com.driveit.driveit.category.CategoryService;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.model.ModelRepository;
import com.driveit.driveit.model.ModelService;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationRepository;
import com.driveit.driveit.motorization.MotorizationService;
import com.driveit.driveit.reservationvehicle.ReservationVehicleService;
import jakarta.transaction.Transactional;
import org.springdoc.core.converters.ModelConverterRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service de gestion des véhicules
 * Liste des opérations possibles :
 * - Récupérer tous les véhicules
 * - Récupérer un véhicule par son identifiant
 * - Sauvegarder un véhicule
 * - Supprimer un véhicule
 *
 * @see Vehicle
 * @see VehicleRepository
 */
@Service
public class VehicleService {

    /**
     * Repository permettant d'effectuer des opérations sur les véhicules
     */
    private final VehicleRepository vehicleRepository;
    private final ReservationVehicleService reservationVehicleService;
    private final ModelRepository modelRepository;
    private final MotorizationRepository motorizationRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CategoryService categoryService;
    private final ModelService modelService;
    private final BrandService brandService;
    private final ModelConverterRegistrar modelConverterRegistrar;
    private final MotorizationService motorizationService;
    private final View error;


    /**
     * Constructeur.
     *
     * @param vehicleRepository      le repository des véhicules
     * @param modelRepository        le repository des modèles
     * @param motorizationRepository le repository des motorisations
     * @param categoryRepository     le repository des catégories
     * @param brandRepository        le repository des marques
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ModelRepository modelRepository, MotorizationRepository motorizationRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ReservationVehicleService reservationVehicleService, CategoryService categoryService, ModelService modelService, BrandService brandService, ModelConverterRegistrar modelConverterRegistrar, MotorizationService motorizationService, View error) {
        this.reservationVehicleService = reservationVehicleService;
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
        this.motorizationRepository = motorizationRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.categoryService = categoryService;
        this.modelService = modelService;
        this.brandService = brandService;
        this.modelConverterRegistrar = modelConverterRegistrar;
        this.motorizationService = motorizationService;
        this.error = error;
    }


    public List<VehicleDto> getAllAvailableVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAllAvailableVehicles();
        List<VehicleDto> vehicleDtos = new ArrayList<>();
        for (Vehicle v : vehicles) {
            vehicleDtos.add(Mapper.vehicleToDto(v));
        }
        return vehicleDtos;
    }



    /**
     * Cette méthode insert un véhicule en base de données.
     *
     * @param vehicleCreateDto le véhicule à ajouter
     * @return une réponse contenant un message de succès ou d'erreur
     */
    @Transactional
    public ResponseEntity<String> insertVehicle(VehicleCreateDto vehicleCreateDto) {

        Response response = new Response();

        System.out.println("je passe par l'insert du vehicle service");

        Brand brand = brandService.findByName(vehicleCreateDto.brand());
        Category category = categoryService.findByName(vehicleCreateDto.category());
        Motorization motorization = motorizationService.findByName(vehicleCreateDto.motorization());

        VehicleRecordDto vehicleRecordDto = new VehicleRecordDto(
                vehicleCreateDto.registration(),
                vehicleCreateDto.numberOfSeats(),
                vehicleCreateDto.service(),
                vehicleCreateDto.url(),
                vehicleCreateDto.emission(),
                motorization,
                new Model(vehicleCreateDto.model(), brand),
                category
        );

        Vehicle vehicle = Mapper.vehicleDtoToEntity(vehicleRecordDto);

        System.out.println(vehicleCreateDto);


        if (vehicle.getStatus() == null) {
            vehicle.setStatus(StatusVehicle.AVAILABLE);
        }

        Model model = vehicle.getModel();

        Optional<Brand> brandExistant = brandRepository.findByName(brand.getName());
        if (brandExistant.isEmpty()) {
            System.out.println("je passe dans le if de la marque");
            brandRepository.save(brand);
        } else {
            model.setBrand(brandExistant.get());
        }

//        Model modelExistant = modelRepository.findFirstByName(model.getName());
        if (modelRepository.findByName(model.getName()) == null) {
            modelRepository.save(model);
        } else {
            vehicle.setModel(modelRepository.findByName(model.getName()));
        }

        Optional<Motorization> motorizationExistante = motorizationRepository.findByName(motorization.getName());
        if (motorizationExistante.isEmpty()) {
            motorizationRepository.save(motorization);
        } else {
            vehicle.setMotorization(motorizationExistante.get());
        }

        Optional<Category> categoryExistante = categoryRepository.findByName(category.getName());
        if (categoryExistante.isEmpty()) {
            categoryRepository.save(category);
        } else {
            vehicle.setCategory(categoryExistante.get());
        }


        if (vehicleRepository.findByRegistration(vehicle.getRegistration()) != null) {
            return ResponseEntity.badRequest().body("Le véhicule avec l'immatriculation " + vehicle.getRegistration() + " existe déjà.");
        }

        System.out.println(vehicle);
        modelRepository.save(vehicle.getModel());
        vehicleRepository.save(vehicle);

        return ResponseEntity.ok("Le véhicule a été ajouté avec succès.");

    }

    /**
     * Récupère tous les véhicules.
     *
     * @return une liste de tous les véhicules
     */
    public List<Vehicle> getAllVehicles() { //à voir si on la garde ou pas
        return vehicleRepository.findAll();
    }

    /**
     * Récupère tous les véhicules de service.
     *
     * @return une liste de tous les véhicules de service
     */
    public List<Vehicle> getAllServiceVehicles() {
        return vehicleRepository.findAllServiceVehicles();
    }

    /**
     * Convertit une liste de {@link Vehicle} en {@link VehicleDto}
     *
     * @param vehicles liste de véhicule à convertir
     * @return liste de véhicules convertis en {@link VehicleDto}
     */
    public List<VehicleDto> getAllVehiclesDto(List<Vehicle> vehicles) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        for (Vehicle v : vehicles) {
            vehicleDtoList.add(Mapper.vehicleToDto(v));
        }
        return vehicleDtoList;
    }

    public ResponseEntity<?> getAllServiceVehiclesDto() {
        List<Vehicle> serviceVehicles = vehicleRepository.findAllServiceVehicles();
        if (serviceVehicles == null || serviceVehicles.isEmpty()) {
            return ResponseEntity.badRequest().body("Aucun véhicule de service n'a été trouvé.");
        } else {
            List<VehicleDto> vehicleDtos = getAllVehiclesDto(serviceVehicles);
            return ResponseEntity.ok(vehicleDtos);
        }
    }

    /**
     * Récupère un véhicule par son identifiant.
     *
     * @param id l'identifiant du véhicule
     * @return le véhicule correspondant à l'identifiant
     */
    public ResponseEntity<?> getServiceVehicleDtoById(int id) {
        Vehicle serviceVehicle = vehicleRepository.findServiceVehicleById(id);
        if (serviceVehicle == null) {
            return ResponseEntity.badRequest().body("Le véhicule avec l'id n°" + id + " n'a pas été trouvé car il est soit inexistant soit n'est pas un véhicule de service.");
        } else {
            VehicleDto vehicleDto = Mapper.vehicleToDto(serviceVehicle);
            return ResponseEntity.ok(vehicleDto);
        }
    }

    /**
     * Met à jour un véhicule existant.
     *
     * @param id      l'identifiant du véhicule à mettre à jour
     * @param vehicle les nouvelles informations du véhicule
     */
    @Transactional
    public ResponseEntity<String> updateVehicle(int id, Vehicle vehicle) {
        Vehicle vehicleExistant = vehicleRepository.findById(id).orElse(null);

        System.out.println("je passe par l'update du vehicle service avant le if");
        if (vehicleExistant != null) {
            System.out.println("je passe par l'update du vehicle service dans le if");

            vehicleExistant.setRegistration(vehicle.getRegistration());
            vehicleExistant.setNumberOfSeats(vehicle.getNumberOfSeats());
            vehicleExistant.setService(vehicle.getService());
            vehicleExistant.setUrl(vehicle.getUrl());
            vehicleExistant.setEmission(vehicle.getEmission());
            vehicleExistant.setStatus(vehicle.getStatus());

            Brand brand = vehicle.getModel().getBrand();
            Model model = vehicle.getModel();
            Motorization motorization = vehicle.getMotorization();
            Category category = vehicle.getCategory();

            Optional<Brand> brandExistant = brandRepository.findByName(brand.getName());
            if (brandExistant.isEmpty()) {
                brandRepository.save(brand);
            } else {
                model.setBrand(brandExistant.get());
            }

            Model modelExistant = modelRepository.findByName(model.getName());
            if (modelExistant == null) {
                modelRepository.save(model);
            } else {
                vehicleExistant.setModel(modelExistant);
            }

            Optional<Motorization> motorizationExistant = motorizationRepository.findByName(motorization.getName());
            if (motorizationExistant.isEmpty()) {
                motorizationRepository.save(motorization);
            } else {
                vehicleExistant.setMotorization(motorizationExistant.get());
            }

            Optional<Category> categoryExistant = categoryRepository.findByName(category.getName());
            if (categoryExistant.isEmpty()) {
                categoryRepository.save(category);
            } else {
                vehicleExistant.setCategory(categoryExistant.get());
            }

            vehicleRepository.save(vehicleExistant);
            return ResponseEntity.ok("Le véhicule a été mis à jour avec succès.");
        } else {
            return ResponseEntity.badRequest().body("Le véhicule avec l'id n°" + id + " n'a pas été trouvé.");
        }
    }

    /**
     * Cette méthode permet de supprimer un vehicule
     *
     * @param id : le vehicule à supprimer
     * @return
     */
    @Transactional
    public ResponseEntity<String> deleteVehicle(int id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (vehicleRepository.findServiceVehicleById(id) == null) {
            return ResponseEntity.badRequest().body("Le véhicule avec l'id n°" + id + " ne peut pas être supprimé car il n'a pas été trouvé.");
        }
        if (reservationVehicleService.isAvailableBetweenDateTimes(id, startDateTime,endDateTime) == true) {
            vehicleRepository.deleteById(id);
            return ResponseEntity.ok("Le véhicule a été supprimé avec succès.");
        } else {
            return ResponseEntity.badRequest().body("Le véhicule avec l'id n°" + id + " ne peut pas être supprimé car il est en cours d'utilisation.");
        }
    }

    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

}
