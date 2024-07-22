package com.driveit.driveit.reservationcarpooling;

/**
 * Cette énumération représente les différents statuts d'une réservation.
 * Une réservation peut être :
 * - Acceptée
 * - En attente
 * - Refusée
 */
public enum StatusReservationCarpooling {
    ACCEPTED("acceptée"), PENDING("en attente"), REFUSED("refusée");

    /**
     * Statut de la réservation
     */
    private final String status;

    /**
     * Constructeur
     * @param status : le statut de la réservation
     */
    StatusReservationCarpooling(String status) {
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
