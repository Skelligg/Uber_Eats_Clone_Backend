package be.kdg.prog6.restaurant.adaptor.out.restaurant;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.*;
import be.kdg.prog6.restaurant.port.out.restaurant.LoadRestaurantPort;
import be.kdg.prog6.restaurant.port.out.restaurant.UpdateRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RestaurantJpaAdaptor implements UpdateRestaurantPort, LoadRestaurantPort {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantJpaAdaptor.class);

    private final RestaurantJpaRepository restaurants;

    public RestaurantJpaAdaptor(RestaurantJpaRepository restaurantJpaRepository) {
        this.restaurants = restaurantJpaRepository;
    }

    @Override
    public Optional<Restaurant> findByOwnerId(UUID ownerId) {
        return restaurants.findByOwnerId(ownerId)
                .map(this::toDomain);
    }

    @Override
    public Optional<Restaurant> findById(RestaurantId restaurantId) {
        return restaurants.findById(restaurantId.id())
                .map(this::toDomain);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        logger.info("Pictures before saving: {}", restaurant.getPictureList().stream().map(Picture::url).toList());
        restaurants.save(toJpaEntity(restaurant));
        return restaurant;
    }

    private Restaurant toDomain(RestaurantJpaEntity entity) {
        return new Restaurant(
                new RestaurantId(entity.getId()),
                new OwnerId(entity.getOwnerId(), entity.getOwnerName()),
                entity.getName(),
                new Address(
                        entity.getStreet(),
                        entity.getNumber(),
                        entity.getPostalCode(),
                        entity.getCity(),
                        entity.getCountry()
                ),
                new EmailAddress(entity.getEmailAddress()),
                entity.getPictures().stream()
                        .map(Picture::new)
                        .collect(Collectors.toList()),
                entity.getCuisineType(),
                new PrepTime(entity.getMinPrepTime(), entity.getMaxPrepTime()),
                new OpeningHours(entity.getOpeningTime(), entity.getClosingTime(), entity.getOpenDays())
        );
    }

    private RestaurantJpaEntity toJpaEntity(Restaurant restaurant) {
        return new RestaurantJpaEntity(
                restaurant.getRestaurantId().id(),
                restaurant.getOwnerId().id(),
                restaurant.getOwnerId().name(),
                restaurant.getName(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().postalCode(),
                restaurant.getAddress().city(),
                restaurant.getAddress().country(),
                restaurant.getEmailAddress().emailAddress(),
                restaurant.getPictureList().stream()
                        .map(Picture::url)
                        .collect(Collectors.toList()),
                restaurant.getCuisineType(),
                restaurant.getDefaultPrepTime().minTime(),
                restaurant.getDefaultPrepTime().maxTime(),
                restaurant.getOpeningHours().openingTime(),
                restaurant.getOpeningHours().closingTime(),
                restaurant.getOpeningHours().openDays()
        );
    }


}
