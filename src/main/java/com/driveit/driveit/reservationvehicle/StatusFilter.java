package com.driveit.driveit.reservationvehicle;

/**
 * Cette énumération représente les différents statuts de filtre possible par rapport aux réservations des véhicules de services.
 * Une réservation peut être :
 * - Passée
 * - En cours
 * - À venir
 */
public enum StatusFilter {
    PAST("passée"),
    IN_PROGRESS("en cours"),
    INCOMING("à venir");

    /**
     * Statut de la réservation
     */
    private final String filter;

    /**
     * Constructeur
     * @param filter : le statut de la réservation
     */
    StatusFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Retourne le statut de la réservation
     * @return Le statut de la réservation
     */
    public String getFilter() {
        return filter;
    }
}
