package com.driveit.driveit.vehicle;

import com.driveit.driveit.category.Category;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.motorization.Motorization;

public record VehicleRecordDto(String registration, int numberOfSeats, boolean service, String url,
                               Double emission, Motorization motorization, Model model,
                               Category category) {
}
