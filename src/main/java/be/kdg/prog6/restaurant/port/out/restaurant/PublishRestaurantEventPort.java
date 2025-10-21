package be.kdg.prog6.restaurant.port.out.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;

public interface PublishRestaurantEventPort {
    void publishRestaurantCreated(Restaurant restaurant);
}
