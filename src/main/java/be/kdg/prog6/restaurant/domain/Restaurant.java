package be.kdg.prog6.restaurant.domain;

import be.kdg.prog6.restaurant.domain.vo.*;

import java.util.List;

public class Restaurant {
    private OwnerId ownerId;
    private RestaurantId restaurantId;

    private String name;
    private Address address;
    private EmailAddress emailAddress;
    private List<Picture> pictureList;
    private CUISINE_TYPE cuisineType;
    private PrepTime defaultPrepTime;
    private OpeningHours openingHours;

    private List<Dish> dishList;

    public Restaurant(OwnerId ownerId, String name, Address address, EmailAddress emailAddress, CUISINE_TYPE cuisineType, PrepTime defaultPrepTime, OpeningHours openingHours) {
        this.ownerId = ownerId;
        this.restaurantId = RestaurantId.newId();
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.cuisineType = cuisineType;
        this.defaultPrepTime = defaultPrepTime;
        this.openingHours = openingHours;
    }

    public String getName() {
        return name;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }
}
