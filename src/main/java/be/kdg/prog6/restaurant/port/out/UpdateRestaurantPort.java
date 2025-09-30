package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.Restaurant;

public interface UpdateRestaurantPort {
    Restaurant addRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Restaurant restaurant);
}
