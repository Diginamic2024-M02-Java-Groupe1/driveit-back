package com.driveit.driveit.carpooling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CarpoolingCreateDto(
        LocalDateTime departureDateTime,
        LocalDateTime arrivalDateTime,
        Address departureAddress,
        Address arrivalAddress,
        int vehicle
) {
    public record Address(
            String number,
            String street,
            String type,
            String city,
            String zipcode
    ) {
        @Override
        public String toString() {
            return "Address{" +
                    "number='" + number + '\'' +
                    ", street='" + street + '\'' +
                    ", type='" + type + '\'' +
                    ", city='" + city + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarpoolingCreateDto{" +
                "departureDateTime=" + departureDateTime +
                ", arrivalDateTime=" + arrivalDateTime +
                ", departureAddress=" + departureAddress +
                ", arrivalAddress=" + arrivalAddress +
                ", vehicle=" + vehicle +
                '}';
    }
}
