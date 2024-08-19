package com.driveit.driveit.carpooling;


import com.driveit.driveit._exceptions.NotFoundException;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit._utils.Validator;
import com.driveit.driveit.address.Address;
import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.address.AddressService;
import com.driveit.driveit.cityzipcode.CityZipCodeService;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorService;
import com.driveit.driveit.reservationcarpooling.ReservationCarpooling;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingService;
import com.driveit.driveit.reservationcarpooling.StatusReservationCarpooling;
import com.driveit.driveit.vehicle.Vehicle;
import com.driveit.driveit.vehicle.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Cette classe est un service qui gère les opérations sur les covoiturages
 * Elle est utilisée pour supprimer ou ajouter un covoiturage etc ...
 *
 * @see Carpooling
 * @see CarpoolingRepository
 */
@Service
public class CarpoolingService {

    private final VehicleService vehicleService;
    private final CollaboratorService collaboratorService;
    private final CityZipCodeService cityZipCodeService;
    private final AddressService addressService;
    private final CarpoolingRepository carpoolingRepository;
    private final ReservationCarpoolingService reservationCarpoolingService;


    /**
     * Constructeur du service des covoiturages
     *
     * @param carpoolingRepository le repository des covoiturages
     */
    @Autowired
    public CarpoolingService(CarpoolingRepository carpoolingRepository, VehicleService vehicleService, CollaboratorService collaboratorService, CityZipCodeService cityZipCodeService, AddressService addressService, ReservationCarpoolingService reservationCarpoolingService) {
        this.carpoolingRepository = carpoolingRepository;
        this.vehicleService = vehicleService;
        this.collaboratorService = collaboratorService;
        this.cityZipCodeService = cityZipCodeService;
        this.addressService = addressService;
        this.reservationCarpoolingService = reservationCarpoolingService;
    }

    /**
     * Méthode pour ajouter un covoiturage
     *
     * @param carpooling le covoiturage à ajouter
     * @return le covoiturage ajouté
     */
    @Transactional
    public Carpooling insert(BodyCarpoolingDto carpooling) throws IllegalArgumentException, NullPointerException, NotFoundException {
        // Vérification du covoiturage
        Objects.requireNonNull(carpooling, "Carpooling must not be null");
        // Vérification du véhicule
        Collaborator organizer = collaboratorService.getCollaboratorById(carpooling.organizerId());
        Objects.requireNonNull(organizer, "Organizer not found");
        // Vérification du véhicule
        Vehicle vehicle = vehicleService.getVehicleById(carpooling.vehicleId());
        Objects.requireNonNull(vehicle, "Vehicle not found");
        AddressDto departureAddress = carpooling.departureAddress();
        AddressDto arrivalAddress = carpooling.arrivalAddress();
        Address departureAddressEntity = new Address(
                departureAddress.getStreetNumber(),
                departureAddress.getStreetName(),
                cityZipCodeService.getCityZipCodeByCityAndZipcodeOrCreate(
                        departureAddress.getCityZipCode().getCity(),
                        departureAddress.getCityZipCode().getCode()
                )
        );
        Address arrivalAddressEntity = new Address(
                arrivalAddress.getStreetNumber(),
                arrivalAddress.getStreetName(),
                cityZipCodeService.getCityZipCodeByCityAndZipcodeOrCreate(
                        arrivalAddress.getCityZipCode().getCity(),
                        arrivalAddress.getCityZipCode().getCode()
                )
        );
        Validator.validateAddress(departureAddressEntity);
        Validator.validateAddress(arrivalAddressEntity);
        Carpooling newCarpooling = new Carpooling(
                carpooling.departureDate(),
                carpooling.departureDate(),
                organizer,
                departureAddressEntity,
                arrivalAddressEntity,
                List.of(),
                vehicle
        );
        Validator.validateCarpooling(newCarpooling);
        addressService.save(departureAddressEntity);
        addressService.save(arrivalAddressEntity);
        carpoolingRepository.save(newCarpooling);
        return newCarpooling;
    }


    /**
     * Méthode pour obtenir un covoiturage par son ID
     *
     * @param id l'identifiant du covoiturage
     * @return le covoiturage
     */
    public CarpoolingDto getCarpoolingById(int id) {

        return Mapper.carpoolingToDto(Objects.requireNonNull(carpoolingRepository.findById(id).orElse(null)));
    }

    /**
     * Méthode pour obtenir la liste des covoiturages
     *
     * @return la liste des covoiturages
     */
    public List<CarpoolingDto> getCarpoolingsByOrganizer(int organizerId) throws NotFoundException {
        Collaborator organizer = collaboratorService.getCollaboratorById(organizerId);
        return organizer.getOrganizedCarpoolings().stream().map(Mapper::carpoolingToDto).toList();
    }


    /**
     * Méthode pour modifier un covoiturage
     *
     * @param carpoolingDto le covoiturage à modifier
     * @return le covoiturage modifié
     */
    @Transactional
    public Carpooling update(int id, BodyCarpoolingDto carpoolingDto) throws NotFoundException {
        // Vérification du covoiturage
        Carpooling carpooling = carpoolingRepository.findById(id).orElse(null);
        Objects.requireNonNull(carpooling, "Carpooling not found");
        // Vérification de l'organisateur
        Collaborator organizer = collaboratorService.getCollaboratorById(carpoolingDto.organizerId());
        Objects.requireNonNull(organizer, "Organizer not found");
        // Vérification du véhicule
        Vehicle vehicle = vehicleService.getVehicleById(carpoolingDto.vehicleId());
        Objects.requireNonNull(vehicle, "Vehicle not found");
        // récupération des adresses
        AddressDto departureAddress = carpoolingDto.departureAddress();
        AddressDto arrivalAddress = carpoolingDto.arrivalAddress();
        Address departureAddressEntity = addressService.getAddressById(departureAddress.getId());
        Address arrivalAddressEntity = addressService.getAddressById(arrivalAddress.getId());
        departureAddressEntity.setStreetNumber(departureAddress.getStreetNumber());
        departureAddressEntity.setStreetName(departureAddress.getStreetName());
        departureAddressEntity.setCityZipCode(cityZipCodeService.getCityZipCodeByCityAndZipcodeOrCreate(
                departureAddress.getCityZipCode().getCity(),
                departureAddress.getCityZipCode().getCode()
        ));

        arrivalAddressEntity.setStreetNumber(arrivalAddress.getStreetNumber());
        arrivalAddressEntity.setStreetName(arrivalAddress.getStreetName());
        arrivalAddressEntity.setCityZipCode(cityZipCodeService.getCityZipCodeByCityAndZipcodeOrCreate(
                arrivalAddress.getCityZipCode().getCity(),
                arrivalAddress.getCityZipCode().getCode()
        ));
        // Validation des adresses
        Validator.validateAddress(departureAddressEntity);
        Validator.validateAddress(arrivalAddressEntity);
        carpooling.setDepartureDate(carpoolingDto.departureDate());
        carpooling.setArrivalDate(carpoolingDto.arrivalDate());
        carpooling.setOrganizer(organizer);
        carpooling.setDepartureAddress(departureAddressEntity);
        carpooling.setArrivalAddress(arrivalAddressEntity);
        carpooling.setVehicle(vehicle);
        // Validation du covoiturage
        Validator.validateCarpooling(carpooling);
        addressService.save(departureAddressEntity);
        addressService.save(arrivalAddressEntity);
        carpoolingRepository.save(carpooling);
        return carpooling;
    }

    /**
     * Méthode pour ajouter un participant à un covoiturage
     *
     * @param carpoolingId  l'identifiant du covoiturage
     * @param participantId l'identifiant du participant
     * @return le covoiturage
     */
    @Transactional
    public Carpooling addParticipant(int carpoolingId, int participantId) throws NotFoundException {
        // Vérification du covoiturage
        Carpooling carpooling = carpoolingRepository.findById(carpoolingId).orElse(null);
        Objects.requireNonNull(carpooling, "Carpooling not found");
        // Vérification du participant
        Collaborator participant = collaboratorService.getCollaboratorById(participantId);
        Objects.requireNonNull(participant, "Participant not found");
        // creation de la réservation
        ReservationCarpooling reservationCarpooling = new ReservationCarpooling(carpooling, participant, StatusReservationCarpooling.PENDING);
        carpooling.getReservations().add(reservationCarpooling);
        reservationCarpoolingService.save(reservationCarpooling);
        carpoolingRepository.save(carpooling);
        return carpooling;
    }

    /**
     * Méthode pour supprimer un participant d'un covoiturage
     *
     * @param id            l'identifiant du covoiturage
     * @param participantId l'identifiant du participant
     * @return le covoiturage
     */
    @Transactional
    public Carpooling removeParticipant(int id, int participantId) {
        // Vérification de la réservation
        ReservationCarpooling reservationCarpooling = reservationCarpoolingService.findByCarpoolingIdAndCollaboratorId(id, participantId);
        Objects.requireNonNull(reservationCarpooling, "Reservation not found");
        // Suppression de la réservation
        Carpooling carpooling = reservationCarpooling.getCarpooling();
        carpooling.getReservations().remove(reservationCarpooling);
        reservationCarpoolingService.delete(reservationCarpooling);
        carpoolingRepository.save(carpooling);
        return carpooling;
    }

    /**
     * Méthode pour accepter une demande de participation à un covoiturage
     *
     * @param id l'identifiant du covoiturage
     * @param participantId l'identifiant du participant
     * @return le covoiturage
     */
    @Transactional
    public Carpooling acceptParticipant(int id, int participantId) {
        // Vérification de la réservation
        ReservationCarpooling reservationCarpooling = reservationCarpoolingService.findByCarpoolingIdAndCollaboratorId(id, participantId);
        Objects.requireNonNull(reservationCarpooling, "Reservation not found");
        // changement du statut de la réservation
        reservationCarpooling.setStatus(StatusReservationCarpooling.ACCEPTED);
        reservationCarpoolingService.save(reservationCarpooling);
        return reservationCarpooling.getCarpooling();
    }

    /**
     * Méthode pour refuser une demande de participation à un covoiturage
     *
     * @param id l'identifiant du covoiturage
     * @param participantId l'identifiant du participant
     * @return le covoiturage
     */
    @Transactional
    public Carpooling refuseParticipant(int id, int participantId) {
        // Vérification de la réservation
        ReservationCarpooling reservationCarpooling = reservationCarpoolingService.findByCarpoolingIdAndCollaboratorId(id, participantId);
        Objects.requireNonNull(reservationCarpooling, "Reservation not found");
        // changement du statut de la réservation
        reservationCarpooling.setStatus(StatusReservationCarpooling.REFUSED);
        reservationCarpoolingService.save(reservationCarpooling);
        return reservationCarpooling.getCarpooling();
    }


    /**
     * Méthode pour supprimer un covoiturage
     *
     * @param carpoolingId l'identifiant du covoiturage
     * @return le covoiturage
     */
    @Transactional
    public Carpooling delete(int carpoolingId) {
        // Vérification du covoiturage
        Carpooling carpooling = carpoolingRepository.findById(carpoolingId).orElse(null);
        Objects.requireNonNull(carpooling, "Carpooling not found");
        // recuperation des adresses
        int departureAddressId = carpooling.getDepartureAddress().getId();
        int arrivalAddressId = carpooling.getArrivalAddress().getId();
        // suppression du covoiturage et de ses dépendances
        carpooling.getReservations().forEach(reservationCarpoolingService::delete);
        carpoolingRepository.delete(carpooling);
        addressService.deleteAddressById(departureAddressId);
        addressService.deleteAddressById(arrivalAddressId);
        return carpooling;
    }


}
