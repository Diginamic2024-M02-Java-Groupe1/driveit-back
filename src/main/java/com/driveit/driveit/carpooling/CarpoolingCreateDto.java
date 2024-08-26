package com.driveit.driveit.carpooling;

import java.time.LocalDate;
import java.time.LocalTime;

public record CarpoolingCreateDto(
        DateTime departureDateTime,
        DateTime arrivalDateTime,
        Address departureAddress,
        Address arrivalAddress,
        int vehicle
) {
    public record DateTime(
            String date,
            String time
    ) {
        @Override
        public String toString() {
            return "DateTime{" +
                    "date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }
    public record Address(
            String number,
            String street,
            String type,
            String zipcode
    ) {
        @Override
        public String toString() {
            return "Address{" +
                    "number='" + number + '\'' +
                    ", street='" + street + '\'' +
                    ", type='" + type + '\'' +
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
