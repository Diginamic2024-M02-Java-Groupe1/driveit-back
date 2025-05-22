package com.driveit.driveit.vehicle;

import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.brand.BrandRepository;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.category.CategoryRepository;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.model.ModelRepository;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationDto;
import com.driveit.driveit.motorization.MotorizationRepository;
import com.driveit.driveit.reservationvehicle.ReservationVehicleService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.driveit.driveit._utils.Mapper.vehicleToDto;


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


    public List<VehicleDto> getAllAvailableVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAllAvailableVehicles();
        List<VehicleDto> vehicleDtos = new ArrayList<>();
        for (Vehicle v : vehicles) {
            vehicleDtos.add(vehicleToDto(v));
        }
        return vehicleDtos;
    }

    private Brand getBrandOrCreate(String brand) {
        Optional<Brand> brandExistant = brandRepository.findByName(brand);
        return brandExistant.orElseGet(() -> brandRepository.save(new Brand(brand)));
    }

    private Motorization getMotorizationOrCreate(String motorization) {
        Optional<Motorization> motorizationExistant = motorizationRepository.findByName(motorization);
        return motorizationExistant.orElseGet(() -> motorizationRepository.save(new Motorization(motorization)));
    }

    private Category getCategoryOrCreate(String category) {
        Optional<Category> categoryExistant = categoryRepository.findByName(category);
        return categoryExistant.orElseGet(() -> categoryRepository.save(new Category(category)));
    }


    /**
     * Cette méthode insert un véhicule en base de données.
     *
     * @param vehicleCreateDto le véhicule à ajouter
     * @return une réponse contenant un message de succès ou d'erreur
     */
    @Transactional
    public VehicleDto insertVehicle(VehicleCreateDto vehicleCreateDto) {

        Brand brand = getBrandOrCreate(vehicleCreateDto.model().getBrand().getName());
        Model model = modelRepository.findByName(vehicleCreateDto.model().getName()).orElseGet(() -> {
            Model newModel = new Model(vehicleCreateDto.model().getName(), brand);
            return modelRepository.save(newModel);
        });
        Category category = getCategoryOrCreate(vehicleCreateDto.category().getName());
        Motorization motorization = getMotorizationOrCreate(vehicleCreateDto.motorization().getName());

        VehicleRecordDto vehicleRecordDto = new VehicleRecordDto(
                vehicleCreateDto.registration(),
                vehicleCreateDto.numberOfSeats(),
                vehicleCreateDto.service(),
                vehicleCreateDto.url(),
                vehicleCreateDto.emission(),
                motorization,
                model,
                category
        );

        Vehicle vehicle = Mapper.vehicleDtoToEntity(vehicleRecordDto);

        if (vehicle.getStatus() == null) {
            vehicle.setStatus(StatusVehicle.AVAILABLE);
        }

        if (vehicleRepository.findByRegistration(vehicle.getRegistration()) != null) {
            throw new IllegalArgumentException("Le véhicule avec l'immatriculation " + vehicle.getRegistration() + " existe déjà.");
        }

        modelRepository.save(vehicle.getModel());
        vehicleRepository.save(vehicle);

        return vehicleToDto(vehicle);

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
            vehicleDtoList.add(vehicleToDto(v));
        }
        return vehicleDtoList;
    }

    public List<VehicleDto> getAllServiceVehiclesDto() {
        List<Vehicle> serviceVehicles = vehicleRepository.findAllServiceVehicles();
        if (serviceVehicles == null || serviceVehicles.isEmpty()) {
            throw new IllegalArgumentException("Aucun véhicule de service trouvé.");
        } else {
            return getAllVehiclesDto(serviceVehicles);
        }
    }

    /**
     * Récupère un véhicule par son identifiant.
     *
     * @param id l'identifiant du véhicule
     * @return le véhicule correspondant à l'identifiant
     */
    public VehicleDto getServiceVehicleDtoById(int id) {
        Vehicle serviceVehicle = vehicleRepository.findServiceVehicleById(id);
        if (serviceVehicle == null) {
            throw new IllegalArgumentException("Aucun véhicule de service trouvé avec l'identifiant " + id);
        } else {
            return vehicleToDto(serviceVehicle);

        }
    }

    /**
     * Met à jour un véhicule existant.
     *
     * @param id l'identifiant du véhicule à mettre à jour
     * @param vehicle les nouvelles informations du véhicule
     */
    @Transactional
    public VehicleDto updateVehicle(VehicleDto vehicle) {
        Vehicle vehicleExistant = vehicleRepository.findById(vehicle.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + vehicle.getId()));

            vehicleExistant.setRegistration(vehicle.getRegistration());
            vehicleExistant.setNumberOfSeats(vehicle.getNumberOfSeats());
            vehicleExistant.setService(vehicle.getService());
            vehicleExistant.setUrl(vehicle.getUrl());
            vehicleExistant.setEmission(vehicle.getEmission());
            vehicleExistant.setStatus(vehicle.getStatus());

            @NotNull(message = "Le modèle du véhicule doit être renseigné.") ModelDto model = vehicle.getModel();
            @NotNull(message = "La motorisation du véhicule doit être renseignée.") MotorizationDto motorization = vehicle.getMotorization();
            @NotNull(message = "La catégorie du véhicule doit être renseignée.") CategoryDto category = vehicle.getCategory();

            Optional<Model> modelExistant = modelRepository.findByName(model.getName());

            if (modelExistant.isPresent()) {
                Brand brand = getBrandOrCreate(model.getBrand().getName());
                modelExistant.get().setBrand(brand);
            } else {
                vehicleExistant.setModel(new Model(model.getName(), getBrandOrCreate(model.getBrand().getName())));
            }

            vehicleExistant.setMotorization(getMotorizationOrCreate(motorization.getName()));

            vehicleExistant.setCategory(getCategoryOrCreate(category.getName()));

            vehicleRepository.save(vehicleExistant);
            return vehicleToDto(vehicleExistant);
        }

    /**
     * Cette méthode permet de supprimer un vehicule
     *
     * @param id : le vehicule à supprimer
     * @return
     */
    @Transactional
    public VehicleDto deleteVehicle(int id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Vehicle vehicle = vehicleRepository.findServiceVehicleById(id);
        if (vehicle == null) {
            throw new IllegalArgumentException("Aucun véhicule de service trouvé avec l'identifiant " + id);
        }
        if (reservationVehicleService.isAvailableBetweenDateTimes(id, startDateTime, endDateTime)) {
            VehicleDto dto = Mapper.vehicleToDto(vehicle);
            vehicleRepository.deleteById(id);
            return dto;
        } else {
            throw new IllegalArgumentException("Le véhicule n'est pas disponible entre " + startDateTime + " et " + endDateTime);
        }
    }

    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * Méthode pour trouver des marques par leur nom
     * @param name le nom de la marque
     * @return la liste des marques trouvées
     */
    @Transactional
    public List<Brand> getBrandsByName(String name) {
        if (name != null && !name.isEmpty()) {
            return brandRepository.findByNameContainingIgnoreCase(name);
        } else {
            return brandRepository.findAll();
        }
    }
}
