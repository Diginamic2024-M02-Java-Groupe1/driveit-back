package com.driveit.driveit._utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Converter {

    public static LocalDateTime stringToLocalDateTime (String date, String time) {
        return LocalDate.parse(date).atTime(LocalTime.parse(time));
    }
}
