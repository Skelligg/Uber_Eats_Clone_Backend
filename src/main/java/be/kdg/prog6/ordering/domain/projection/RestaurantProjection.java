package be.kdg.prog6.ordering.domain.projection;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class RestaurantProjection {

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

    private List<String> openDays;

    protected RestaurantProjection() {
        // JPA only
    }

    public RestaurantProjection(UUID restaurantId,
                                UUID ownerId,
                                String name,
                                String street,
                                String number,
                                String postalCode,
                                String city,
                                String country,
                                String emailAddress,
                                List<String> pictures,
                                String cuisineType,
                                int minPrepTime,
                                int maxPrepTime,
                                LocalTime openingTime,
                                LocalTime closingTime,
                                List<String> openDays) {
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
        this.openDays = openDays;
    }

    // Getters (you might not need setters if projection is append-only)

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public int getMinPrepTime() {
        return minPrepTime;
    }

    public int getMaxPrepTime() {
        return maxPrepTime;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public boolean isOpenNow() {
        LocalTime now = LocalTime.now();
        return !now.isBefore(openingTime) && !now.isAfter(closingTime);
    }

    public List<String> getOpenDays() {
        return openDays;
    }
}
