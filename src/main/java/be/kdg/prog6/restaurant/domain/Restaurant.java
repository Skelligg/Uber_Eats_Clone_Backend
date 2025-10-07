package be.kdg.prog6.restaurant.domain;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.restaurant.domain.vo.restaurant.*;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private RestaurantId restaurantId;
    private OwnerId ownerId;

    private String name;
    private Address address;
    private EmailAddress emailAddress;
    private List<Picture> pictureList;
    private CUISINE_TYPE cuisineType;
    private PrepTime defaultPrepTime;
    private OpeningHours openingHours;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Restaurant(OwnerId ownerId, String name, Address address, EmailAddress emailAddress, CUISINE_TYPE cuisineType, PrepTime defaultPrepTime, OpeningHours openingHours) {
        this.restaurantId = RestaurantId.newId();
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.pictureList = new ArrayList<>();
        this.cuisineType = cuisineType;
        this.defaultPrepTime = defaultPrepTime;
        this.openingHours = openingHours;
    }

    public Restaurant(RestaurantId restaurantId, OwnerId ownerId, String name, Address address, EmailAddress emailAddress,
                      List<Picture> pictures, CUISINE_TYPE cuisineType, PrepTime defaultPrepTime, OpeningHours openingHours) {
        this.restaurantId = restaurantId; // use existing
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.pictureList = pictures != null ? pictures : new ArrayList<>();
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

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public Address getAddress() {
        return address;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public CUISINE_TYPE getCuisineType() {
        return cuisineType;
    }

    public PrepTime getDefaultPrepTime() {
        return defaultPrepTime;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }


    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
}
