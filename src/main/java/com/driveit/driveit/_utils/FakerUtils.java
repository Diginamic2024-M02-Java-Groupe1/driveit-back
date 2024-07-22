package com.driveit.driveit._utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.cityZipCode.City;
import com.driveit.driveit.cityZipCode.CityZipCode;
import com.driveit.driveit.cityZipCode.ZipCode;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.country.Country;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.vehicle.StatusVehicle;
import com.driveit.driveit.vehicle.Vehicle;
import net.datafaker.Faker;

public class FakerUtils {

    public static Collaborator generateFakeCollaborator() {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String role = faker.options().option("collaborator", "admin");

        return new Collaborator(firstName, lastName, role);
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

        return new Vehicle(plate, seats, service, imageUrl, co2Emission, status, motorization, model, category);
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
        Country country = generateFakeCountry();

        return new Address(streetNumber, streetName, cityZipCode, country);
    }

    /**
     * Génère une ville et un code postal fictifs
     * @return une ville et un code postal fictifs
     */
    public static CityZipCode generateFakeCityZipCode() {
        City city = generateFakeCity();
        ZipCode zipCode = generateFakeZipCode();
        CityZipCode result = new CityZipCode(city, zipCode);
        System.out.println(result);
        return result;
    }

    public static ZipCode generateFakeZipCode() {
        Faker faker = new Faker();
        String zipCode =  faker.address().zipCode();
        return new ZipCode(zipCode);
    }

    public static City generateFakeCity() {
        Faker faker = new Faker();
        String city = faker.address().cityName();
        System.out.println(city);
        return new City(city);
    }

    public static Country generateFakeCountry() {
        Faker faker = new Faker();
        String country = faker.address().country();
        return new Country(country);
    }
}
