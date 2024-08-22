package com.driveit.driveit.vehicle;

import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.brand.BrandRepository;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryRepository;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.model.ModelRepository;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationRepository;
import com.driveit.driveit.reservationvehicle.ReservationVehicleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    public VehicleService(VehicleRepository vehicleRepository, ModelRepository modelRepository, MotorizationRepository motorizationRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ReservationVehicleService reservationVehicleService) {
        this.reservationVehicleService = reservationVehicleService;
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
        this.motorizationRepository = motorizationRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    /**
     * Cette méthode insert un véhicule en base de données.
     *
     * @param vehicleDto le véhicule à ajouter
     * @return une réponse contenant un message de succès ou d'erreur
     */
    @Transactional
    public ResponseEntity<String> insertVehicle(VehicleDto vehicleDto) {

        Vehicle vehicle = Mapper.vehicleDtoToEntity(vehicleDto);

        if (vehicle.getStatus() == null) {
            vehicle.setStatus(StatusVehicle.AVAILABLE);
        }

        Brand brand = vehicle.getModel().getBrand();
        Model model = vehicle.getModel();
        Motorization motorization = vehicle.getMotorization();
        Category category = vehicle.getCategory();


        Brand brandExistant = brandRepository.findFirstByName(brand.getName());
        if (brandExistant == null) {
            brandRepository.save(brand);
        } else {
            model.setBrand(brandExistant);
        }

        Model modelExistant = modelRepository.findFirstByName(model.getName());
        if (modelExistant == null) {
            modelRepository.save(model);
        } else {
            vehicle.setModel(modelExistant);
        }

        Motorization motorizationExistante = motorizationRepository.findFirstByName(motorization.getName());
        if (motorizationExistante == null) {
            motorizationRepository.save(motorization);
        } else {
            vehicle.setMotorization(motorizationExistante);
        }

        Category categoryExistante = categoryRepository.findFirstByName(category.getName());
        if (categoryExistante == null) {
            categoryRepository.save(category);
        } else {
            vehicle.setCategory(categoryExistante);
        }

        if (vehicleRepository.findByRegistration(vehicle.getRegistration()) != null) {
            return ResponseEntity.badRequest().body("Le véhicule avec l'immatriculation " + vehicle.getRegistration() + " existe déjà.");
        }

        vehicleRepository.save(vehicle);
        return ResponseEntity.ok("Le véhicule a été inséré avec succès.");
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

        if (vehicleExistant != null) {

            vehicleExistant.setRegistration(vehicle.getRegistration());
            vehicleExistant.setNumberOfSeats(vehicle.getNumberOfSeats());
            vehicleExistant.setService(vehicle.getService());
            vehicleExistant.setUrl(vehicle.getUrl());
            vehicleExistant.setEmission(vehicle.getEmission());
            vehicleExistant.setStatus(vehicle.getStatus());
            vehicleExistant.setCollaborators(vehicle.getCollaborators());

            Motorization motorizationExistant = motorizationRepository.findByName(vehicle.getMotorization().getName());
            if (motorizationExistant == null) {
                motorizationRepository.save(vehicle.getMotorization());
            } else {
                vehicleExistant.setMotorization(motorizationExistant);
            }

            Model modelExistant = modelRepository.findByName(vehicle.getModel().getName());
            if (modelExistant == null) {
                modelRepository.save(vehicle.getModel());
            } else {
                vehicleExistant.setModel(modelExistant);
            }

            Category categoryExistant = categoryRepository.findByName(vehicle.getCategory().getName());
            if (categoryExistant == null) {
                categoryRepository.save(vehicle.getCategory());
            } else {
                vehicleExistant.setCategory(categoryExistant);
            }

            if (vehicleExistant == vehicle) {
                return ResponseEntity.badRequest().body("Le véhicule n'a pas été modifié.");
            } else {
                vehicleRepository.save(vehicleExistant);
                return ResponseEntity.ok("Le véhicule a été mis à jour avec succès.");
            }
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
            return ResponseEntity.badRequest().body("Le véhicule avec l'id n°" + id + " ne peut pas être supprimé car il est en cours d'utilisation.");
        }

        else {
            vehicleRepository.deleteById(id);
            return ResponseEntity.ok("Le véhicule a été supprimé avec succès.");
        }
    }

    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

}
