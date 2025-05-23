package com.driveit.driveit.collaborator;

import jakarta.persistence.Entity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Cette classe représente un administrateur de l'application.
 * Un administrateur est un collaborateur qui a des droits supplémentaires.
 */
@Entity
public class Admin extends Collaborator{
    /**
     * Constructeur avec paramètres.
     * @param email Adresse email de l'administrateur
     * @param password Mot de passe de l'administrateur
     * @param lastName Nom de famille de l'administrateur
     * @param firstName Prénom de l'administrateur
     */
    public Admin(String email, String password, String lastName, String firstName) {
        super(email, password, lastName, firstName);
        setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    /**
     * Constructeur par défaut.
     */
    public Admin() {}
}
