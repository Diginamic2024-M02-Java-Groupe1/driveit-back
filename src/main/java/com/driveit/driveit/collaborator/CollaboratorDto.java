package com.driveit.driveit.collaborator;

/**
 * Cette classe est un DTO qui permet de manipuler les données des collaborateurs
 * Elle est utilisée pour récupérer les données des collaborateurs depuis le front-end
 * et les envoyer au back-end et vice-versa.
 */
public class CollaboratorDto {

    /**
     * L'identifiant du collaborateur
     */
    private int id;

    /**
     * Le nom du collaborateur
     */
    private String lastName;

    /**
     * Le prénom du collaborateur
     */
    private String firstName;

    /**
     * Le rôle du collaborateur
     */
    private String role;
    /**
     * Constructeur par défaut
     */
    public CollaboratorDto() {
    }

    /**
     * Constructeur avec paramètres
     *
     * @param id          : l'identifiant du collaborateur
     * @param lastName        : le nom du collaborateur
     * @param firstName   : le prénom du collaborateur
     * @param role       : le rôle du collaborateur
     */
    public CollaboratorDto(int id, String lastName, String firstName, String role) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant du collaborateur
     *
     * @return L'identifiant du collaborateur
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du collaborateur
     *
     * @param id : le nouvel identifiant du collaborateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom du collaborateur
     *
     * @return Le nom du collaborateur
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Modifie le nom du collaborateur
     *
     * @param lastName : le nouveau nom du collaborateur
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retourne le prénom du collaborateur
     *
     * @return Le prénom du collaborateur
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Modifie le prénom du collabor
     *
     * @param firstName : le nouveau prénom du collaborateur
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retourne le rôle du collaborateur
     *
     * @return Le rôle du collaborateur
     */
    public String getRole() {
        return role;
    }

    /**
     * Modifie le rôle du collaborateur
     *
     * @param role : le nouveau rôle du collaborateur
     */
    public void setRole(String role) {
        this.role = role;
    }






































}
