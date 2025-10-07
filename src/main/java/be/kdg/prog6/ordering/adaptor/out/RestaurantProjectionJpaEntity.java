package be.kdg.prog6.ordering.adaptor.out;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurant_projection", schema = "ordering")
public class RestaurantProjectionJpaEntity {

    @Id
    private UUID id;

    private UUID ownerId;
    private String ownerName;
    private String name;

    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    private String emailAddress;

    @ElementCollection
    @CollectionTable(name = "restaurant_projection_pictures", joinColumns = @JoinColumn(name = "restaurant_projection_id"))
    @Column(name = "url")
    private List<String> pictures;

    private String cuisineType;

    private int minPrepTime;
    private int maxPrepTime;

    private LocalTime openingTime;
    private LocalTime closingTime;


    @ElementCollection
    @CollectionTable(name = "restaurant_projection_open_days", schema = "ordering", joinColumns = @JoinColumn(name="restaurant_projection_id"))
    @Column(name = "day")
    private List<String> openDays;

    protected RestaurantProjectionJpaEntity() {
        // for JPA
    }

    public RestaurantProjectionJpaEntity(UUID id,
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
                                         String cuisineType,
                                         int minPrepTime,
                                         int maxPrepTime,
                                         LocalTime openingTime,
                                         LocalTime closingTime,
                                         List<String> openDays) {
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

    // Getters only (projections are usually read-only)

    public UUID getId() {
        return id;
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

    public List<String> getOpenDays() {
        return openDays;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
