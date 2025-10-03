package be.kdg.prog6.restaurant.adaptor.out;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.port.out.UpdateRestaurantPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEventPublisher implements UpdateRestaurantPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public RestaurantEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        restaurant.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return null;
    }
}
