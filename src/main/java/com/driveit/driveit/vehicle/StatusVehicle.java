package com.driveit.driveit.vehicle;

/**
 * Cette énumération représente les différents statuts d'un véhicule.
 * Un véhicule peut être :
 * - Disponible
 * - Indisponible
 * - En réparation
 */
public enum StatusVehicle {
    AVAILABLE("disponible"),
    UNAVAILABLE("indisponible"),
    MAINTENANCE("en réparation");

    /**
     * Statut du véhicule
     */
    private final String status;

    /**
     * Constructeur
     * @param status : le statut du véhicule
     */
    StatusVehicle(String status) {
        this.status = status;
    }

    /**
     * Retourne le statut du véhicule
     * @return Le statut du véhicule
     */
    public String getStatus() {
        return status;
    }
}
