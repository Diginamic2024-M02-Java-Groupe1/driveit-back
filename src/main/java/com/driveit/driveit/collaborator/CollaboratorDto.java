package com.driveit.driveit.collaborator;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Cette classe est un DTO qui permet de manipuler les données des collaborateurs.
 * Elle est utilisée pour récupérer les données des collaborateurs depuis le front-end
 * et les envoyer au back-end et vice-versa.
 *
 * @param id Identifiant unique du collaborateur
 * @param email Adresse email du collaborateur
 * @param firstName Prénom du collaborateur
 * @param lastName Nom de famille du collaborateur
 * @param authorities Liste des rôles du collaborateur
 */
public record CollaboratorDto(int id, String email, String firstName, String lastName, List<GrantedAuthority> authorities) {
}
