package com.driveit.driveit.collaborator;

/**
 * Cette classe est un DTO qui permet de manipuler les données des collaborateurs
 * Elle est utilisée pour récupérer les données des collaborateurs depuis le front-end
 * et les envoyer au back-end et vice-versa.
 *
 * @param id        L'identifiant du collaborateur
 * @param lastName  Le nom du collaborateur
 * @param firstName Le prénom du collaborateur
 * @param role      Le rôle du collaborateur
 */
public record CollaboratorDto(int id,String email, String firstName, String lastName, String role) { }
