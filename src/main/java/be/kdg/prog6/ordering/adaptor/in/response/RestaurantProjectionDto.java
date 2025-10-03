package be.kdg.prog6.ordering.adaptor.in.response;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class RestaurantProjectionDto {
    private UUID restaurantId;
    private UUID ownerId;
    private String name;
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;
    private String emailAddress;
    private List<String> pictures;
    private String cuisineType;
    private int minPrepTime;
    private int maxPrepTime;
    private LocalTime openingTime;
    private LocalTime closingTime;

    // constructor
    public RestaurantProjectionDto(UUID restaurantId, UUID ownerId, String name, String street, String number,
                                   String postalCode, String city, String country, String emailAddress,
                                   List<String> pictures, String cuisineType, int minPrepTime, int maxPrepTime,
                                   LocalTime openingTime, LocalTime closingTime) {
        this.restaurantId = restaurantId;
        this.ownerId = ownerId;
        this.name = name;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.emailAddress = emailAddress;
        this.pictures = pictures;
        this.cuisineType = cuisineType;
        this.minPrepTime = minPrepTime;
        this.maxPrepTime = maxPrepTime;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    // getters (needed for JSON serialization)
    public String getRestaurantId() { return restaurantId.toString(); }
    public String getOwnerId() { return ownerId.toString(); }
    public String getName() { return name; }
    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getPostalCode() { return postalCode; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getEmailAddress() { return emailAddress; }
    public List<String> getPictures() { return pictures; }
    public String getCuisineType() { return cuisineType; }
    public int getMinPrepTime() { return minPrepTime; }
    public int getMaxPrepTime() { return maxPrepTime; }
    public LocalTime getOpeningTime() { return openingTime; }
    public LocalTime getClosingTime() { return closingTime; }
}
