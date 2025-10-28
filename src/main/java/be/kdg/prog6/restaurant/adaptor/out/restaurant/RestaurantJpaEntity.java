package be.kdg.prog6.restaurant.adaptor.out.restaurant;

import be.kdg.prog6.restaurant.domain.vo.restaurant.CUISINE_TYPE;
import be.kdg.prog6.restaurant.domain.vo.restaurant.DAY;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurant", schema = "restaurant")
public class RestaurantJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String name;

    // Address (flattened)
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    // Email
    private String emailAddress;

    // Pictures (simplified to strings, could later use separate entity)
    @ElementCollection
    @CollectionTable(name = "restaurant_pictures", joinColumns = @JoinColumn(name = "restaurant_id"), schema = "restaurant")
    @Column(name = "url")
    private List<String> pictures;

    // Cuisine type (enum)
    @Enumerated(EnumType.STRING)
    private CUISINE_TYPE cuisineType;

    // PrepTime (flattened)
    private int minPrepTime;
    private int maxPrepTime;

    // Opening hours (flattened)
    private LocalTime openingTime;
    private LocalTime closingTime;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "restaurant_open_days", schema = "restaurant", joinColumns = @JoinColumn(name="restaurant_id"))
    @Column(name = "day")
    private List<DAY> openDays;

    // Constructors
    protected RestaurantJpaEntity() {
    }

    public RestaurantJpaEntity(
            UUID id,
            UUID ownerId,
            String ownerName,
            String name,
            String street,
            String number,
            String postalCode,
            String city,
            String country,
            String emailAddress,
            List<String> pictures,
            CUISINE_TYPE cuisineType,
            int minPrepTime,
            int maxPrepTime,
            LocalTime openingTime,
            LocalTime closingTime,
            List<DAY> openDays
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
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

    // Getters/setters (can use Lombok if allowed)
    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
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

    public CUISINE_TYPE getCuisineType() {
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

    public List<DAY> getOpenDays() {
        return openDays;
    }
}
