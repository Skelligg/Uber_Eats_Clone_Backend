package be.kdg.prog6.restaurant.adaptor.out;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.Picture;
import be.kdg.prog6.restaurant.port.out.LoadRestaurantPort;
import be.kdg.prog6.restaurant.port.out.UpdateRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RestaurantJpaAdaptor implements UpdateRestaurantPort, LoadRestaurantPort {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantJpaAdaptor.class);

    private final RestaurantJpaRepository restaurants;

    public RestaurantJpaAdaptor(RestaurantJpaRepository restaurantJpaRepository) {
        this.restaurants = restaurantJpaRepository;
    }

    @Override
    public Optional<Restaurant> LoadBy(OwnerId ownerId) {
        return Optional.empty();
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        RestaurantJpaEntity restaurantJpaEntity = new RestaurantJpaEntity(
                restaurant.getRestaurantId().id(), // UUID
                restaurant.getOwnerId().id().toString(), // OwnerId flattened
                restaurant.getName(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().postalCode(),
                restaurant.getAddress().city(),
                restaurant.getAddress().country(),
                restaurant.getEmailAddress().emailAddress(),
                restaurant.getPictureList().stream()
                        .map(Picture::url) // assuming Picture has a url() or getUrl()
                        .toList(),
                restaurant.getCuisineType(),
                restaurant.getDefaultPrepTime().minTime(),
                restaurant.getDefaultPrepTime().maxTime(),
                restaurant.getOpeningHours().openingTime(),
                restaurant.getOpeningHours().closingTime(),
                restaurant.getOpeningHours().openDays()
        );
        RestaurantJpaEntity saved = this.restaurants.save(restaurantJpaEntity);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return null;
    }
}
