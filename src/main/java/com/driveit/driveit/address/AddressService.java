package com.driveit.driveit.address;


import com.driveit.driveit.cityzipcode.CityZipCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Cette classe est un service qui gère les opérations sur les adresses
 * Elle est utilisée pour supprimer ou ajouter une adresse etc ...
 *
 * @see Address
 * @see AddressRepository
 */
@Service
public class AddressService {


    /**
     * Le repository des adresses
     */
    private final AddressRepository addressRepository;

    /**
     * Constructeur du service des adresses
     * @param addressRepository le repository des adresses
     **/
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Méthode pour supprimer une adresse
     * @param address l'adresse à supprimer
     */
    @Transactional
    public void delete(Address address) {
        addressRepository.delete(address);
    }

    /**
     * Méthode pour ajouter une adresse
     * @param address l'adresse à ajouter
     */
    @Transactional
    public void save(Address address) {
        addressRepository.save(address);
    }


    /**
     * Méthode pour récupérer une adresse par son identifiant
     *
     * @param id l'identifiant de l'adresse
     * @return l'adresse
     */
    public Address getAddressById(int id) {
        return addressRepository.findById(id).orElse(null);
    }


    /**
     * Méthode pour supprimer une adresse par son identifiant
     * @param id l'identifiant de l'adresse
     */
    @Transactional
    public void deleteAddressById(int id) {
        addressRepository.deleteById(id);
    }
}
