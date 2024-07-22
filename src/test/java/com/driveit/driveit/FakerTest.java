package com.driveit.driveit;

import com.driveit.driveit._utils.FakerUtils;
import com.driveit.driveit.address.Address;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.vehicle.Vehicle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FakerTest {

    @Test
    public void generateFakeCollaborator() {
        Collaborator collaborator = FakerUtils.generateFakeCollaborator();
        assertNotNull(collaborator, "Collaborator should not be null");
        assertNotNull(collaborator.getLastName(), "Last name should be populated");
        assertNotNull(collaborator.getFirstName(), "First name should be populated");
        assertNotNull(collaborator.getRole(), "Role should be populated");
    }

    @Test
    public void generateFakeVehicle() {
        Vehicle vehicle = FakerUtils.generateFakeVehicle();
        assertNotNull(vehicle, "Vehicle should not be null");
        assertNotNull(vehicle.getModel(), "Model should be populated");
        assertNotNull(vehicle.getMotorization(), "Motorization should be populated");
        assertNotNull(vehicle.getModel().getBrand(), "Brand should be populated");
        assertNotNull(vehicle.getRegistration(), "Registration should be populated");
    }

    @Test
    public void generateFakeAddress() {
        Address address = FakerUtils.generateFakeAddress();
        assertNotNull(address, "Address should not be null");
        assertNotNull(address.getStreetName(), "Street should be populated");
        assertNotNull(address.getCityZipCode(), "City and zip code should be populated");
        assertNotNull(address.getCountry(), "Country should be populated");
    }
}
