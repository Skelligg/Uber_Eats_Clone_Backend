package be.kdg.prog6.restaurant.port.out.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;

public interface UpdateRestaurantPort {
    Restaurant addRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Restaurant restaurant);
}
