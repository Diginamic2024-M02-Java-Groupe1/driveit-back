package com.driveit.driveit.reservation;

/**
 * Cette énumération représente les différents statuts d'une réservation.
 * Une réservation peut être :
 * - Acceptée
 * - En attente
 * - Refusée
 */
public enum StatusReservation {
    ACCEPTED("acceptée"), PENDING("en attente"), REFUSED("refusée");

    /**
     * Statut de la réservation
     */
    private final String status;

    /**
     * Constructeur
     * @param status : le statut de la réservation
     */
    StatusReservation(String status) {
        this.status = status;
    }

    /**
     * Retourne le statut de la réservation
     * @return Le statut de la réservation
     */
    public String getStatus() {
        return status;
    }
}
