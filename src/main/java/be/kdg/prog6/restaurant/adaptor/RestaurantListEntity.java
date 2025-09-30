package be.kdg.prog6.restaurant.adaptor;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.port.out.UpdateRestaurantPort;

import java.util.List;

public class RestaurantListEntity implements UpdateRestaurantPort {
    private List<Restaurant> restaurantList;

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return null;
    }
}
