package com.driveit.driveit.reservation;


import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.collaborator.Collaborator;
import jakarta.persistence.*;

/**
 * Cette classe est une entité JPA qui représente un collaborateur participant à un covoiturage.
 * /!\ Cette classe est une table de jointure entre les tables "carpooling" et "collaborator".
 * car elle contient des clés étrangères vers ces deux tables + une colonne "status". (obligation de créer une classe pour attacher une propriété à une table de jointure)
 * Un collaborateur participant à un covoiturage est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un covoiturage
 * - Un collaborateur
 * - Un statut (ex: accepté, en attente, refusé)
 * **/
@Entity
@Table(name = "reservation")
public class Reservation {

    // Identifiant unique de la table de jointure (obligatoir car JPA demande une clé primaire pour chaque table)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Clé étrangère vers la table "carpooling"
    @ManyToOne
    private Carpooling carpooling;

    // Clé étrangère vers la table "collaborator"
    @ManyToOne
    private Collaborator collaborator;

    // Statut du collaborateur dans le covoiturage
    @Column(name = "status", length = 50, nullable = false)
    private String status;

    // Constructeur par défaut
    public Reservation() {
    }

    /**
     * Constructeur avec paramètres
     * @param carpooling : le covoiturage
     * @param collaborator : le collaborateur
     * @param status : le statut du collaborateur
     */
    public Reservation(Carpooling carpooling, Collaborator collaborator, String status) {
        this.carpooling = carpooling;
        this.collaborator = collaborator;
        this.status = status;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant de la table de jointure.
     * @return L'identifiant de la table de jointure.
     */
    public int getId() {
        return id;
    }


    /**
     * Retourne le covoiturage.
     * @return Le covoiturage.
     */
    public Carpooling getCarpooling() {
        return carpooling;
    }

    /**
     * Modifie le covoiturage.
     * @param carpooling Le nouveau covoiturage.
     */
    public void setCarpooling(Carpooling carpooling) {
        this.carpooling = carpooling;
    }

    /**
     * Retourne le collaborateur.
     * @return Le collaborateur.
     */
    public Collaborator getCollaborator() {
        return collaborator;
    }

    /**
     * Modifie le collaborateur.
     * @param collaborator Le nouveau collaborateur.
     */
    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    /**
     * Retourne le statut du collaborateur.
     * @return Le statut du collaborateur.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Modifie le statut du collaborateur.
     * @param status Le nouveau statut du collaborateur.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}