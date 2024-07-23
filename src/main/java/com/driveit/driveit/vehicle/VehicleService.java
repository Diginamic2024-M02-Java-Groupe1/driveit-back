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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final ModelRepository modelRepository;
    private final MotorizationRepository motorizationRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    /**
     * Constructeur.
     *
     * @param vehicleRepository     le repository des véhicules
     * @param modelRepository       le repository des modèles
     * @param motorizationRepository le repository des motorisations
     * @param categoryRepository    le repository des catégories
     * @param brandRepository       le repository des marques
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ModelRepository modelRepository, MotorizationRepository motorizationRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
        this.motorizationRepository = motorizationRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    /**
     * Cette méthode sauvegarde un véhicule.
     *
     * @param vehicle le véhicule à ajouter
     * @return une réponse contenant un message de succès ou d'erreur
     */
    @Transactional
    public ResponseEntity<String> insertVehicle(Vehicle vehicle) {

        Brand brand = vehicle.getModel().getBrand();
        Model model = vehicle.getModel();
        Motorization motorization = vehicle.getMotorization();
        Category category = vehicle.getCategory();

        Brand brandExistant = brandRepository.findByName(brand.getName());
        if (brandExistant == null) {
            brandExistant = brandRepository.save(brand);
        }

        Model modelExistant = modelRepository.findByName(model.getName());
        if (modelExistant == null) {
            model.setBrand(brandExistant);
            modelExistant = modelRepository.save(model);
        } else {
            modelExistant.setBrand(brandExistant);
        }

        Motorization motorizationExistante = motorizationRepository.findByName(motorization.getName());
        if (motorizationExistante == null) {
            motorizationExistante = motorizationRepository.save(motorization);
        }

        Category categoryExistante = categoryRepository.findByName(category.getName());
        if (categoryExistante == null) {
            categoryExistante = categoryRepository.save(category);
        }

        vehicle.setModel(modelExistant);
        vehicle.setMotorization(motorizationExistante);
        vehicle.setCategory(categoryExistante);

        vehicleRepository.save(vehicle);

        return ResponseEntity.ok("VehicleService : le véhicule a été inséré avec succès.");
    }

    /**
     * Récupère tous les véhicules.
     *
     * @return une liste de tous les véhicules
     */
    @Transactional
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }

    /**
     * Converti une liste de {@link Vehicle} en {@link VehicleDto}
     *
     * @param vehicles liste de véhicule à convertir
     * @return liste de véhicule convertie
     */
    public List<VehicleDto> getAllVehiclesDto(List<Vehicle> vehicles) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        for (Vehicle v : vehicles) {
            vehicleDtoList.add(Mapper.vehicleToDto(v));
        }
        return vehicleDtoList;
    }

    /**
     * Met à jour un véhicule existant.
     *
     * @param id      l'identifiant du véhicule à mettre à jour
     * @param vehicle les nouvelles informations du véhicule
     */
    @Transactional
    public void updateVehicle(int id, Vehicle vehicle) {
        Vehicle v = vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
        v.setRegistration(vehicle.getRegistration());
        v.setNumberOfSeats(vehicle.getNumberOfSeats());
        v.setService(vehicle.getService());
        v.setUrl(vehicle.getUrl());
        v.setEmission(vehicle.getEmission());
        v.setStatus(vehicle.getStatus());
        v.setCollaborators(vehicle.getCollaborators());
        v.setMotorization(vehicle.getMotorization());
        v.setModel(vehicle.getModel());
        v.setCategory(vehicle.getCategory());

        vehicleRepository.save(v);
    }

    /**
     * Cette méthode permet de supprimer un vehicule
     *
     * @param id : le vehicule à supprimer
     */
    @Transactional
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }
}
