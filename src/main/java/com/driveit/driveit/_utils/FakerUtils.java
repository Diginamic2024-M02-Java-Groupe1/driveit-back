package com.driveit.driveit._utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.cityzipcode.CityZipCode;
import com.driveit.driveit.collaborator.AccountCreateDto;
import com.driveit.driveit.collaborator.Admin;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.vehicle.StatusVehicle;
import com.driveit.driveit.vehicle.Vehicle;
import net.datafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class FakerUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Collaborator generateFakeCollaborator() {
        Faker faker = new Faker();

        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = passwordEncoder.encode(faker.internet().password());

        return new Collaborator(email, password, firstName, lastName);
    }

    public static Admin generateFakeAdmin() {
        Faker faker = new Faker();

        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = passwordEncoder.encode(faker.internet().password());

        return new Admin(email, password, firstName, lastName);
    }

    public static AccountCreateDto generateFakeAccountCreateDto() {
        Faker faker = new Faker();

        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = faker.internet().password();

        return new AccountCreateDto(email, firstName, lastName, password);
    }

    // Vehicle
    /**
     * Génère un véhicule fictif
     * @return un véhicule fictif
     */
    public static Vehicle generateFakeVehicle() {
        Faker faker = new Faker();

        String plate = faker.bothify("??-###-??");
        int seats = faker.number().numberBetween(2, 8);
        boolean service = faker.bool().bool();
        String imageUrl = faker.internet().url();
        double co2Emission = faker.number().numberBetween(100, 200);
        StatusVehicle status = faker.options().option(StatusVehicle.values());
        Motorization motorization = generateFakeMotorization();
        Model model = generateFakeModel();
        Category category = generateFakeCategory();

        return new Vehicle(plate, seats, service, imageUrl, co2Emission, motorization, model, category);
    }

    public static Brand generateFakeBrand() {
        Faker faker = new Faker();
        String name = faker.vehicle().manufacturer();
        return new Brand(name);
    }

    public static Motorization generateFakeMotorization() {
        Faker faker = new Faker();
        String motorization = faker.options().option("essence", "diesel", "électrique", "hybride");
        return new Motorization(motorization);
    }

    public static Model generateFakeModel() {
        Faker faker = new Faker();
        String name = faker.vehicle().model();
        Brand brand = generateFakeBrand();
        return new Model(name, brand);
    }

    public static Category generateFakeCategory() {
        Faker faker = new Faker();
        String category = faker.options().option("citadine", "berline", "break");
        return new Category(category);
    }

    // Address

    /**
     * Génère une adresse fictive
     * @return une adresse fictive
     */
    public static Address generateFakeAddress() {
        Faker faker = new Faker();

        String streetNumber = faker.address().buildingNumber();
        String streetName = faker.address().streetAddress();
        CityZipCode cityZipCode = generateFakeCityZipCode();


        return new Address(streetNumber, streetName, cityZipCode);
    }

    /**
     * Génère une ville et un code postal fictifs
     * @return une ville et un code postal fictifs
     */
    public static CityZipCode generateFakeCityZipCode() {
        Faker faker = new Faker();
        String city = faker.address().city();
        String zipCode = faker.address().zipCode();
        return new CityZipCode(city, zipCode);
    }

}
