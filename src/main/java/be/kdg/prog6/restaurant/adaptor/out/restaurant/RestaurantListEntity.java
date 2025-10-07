package be.kdg.prog6.restaurant.adaptor.out.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.port.out.LoadRestaurantPort;
import be.kdg.prog6.restaurant.port.out.UpdateRestaurantPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class RestaurantListEntity implements UpdateRestaurantPort, LoadRestaurantPort {
    private static List<Restaurant> restaurantList = new ArrayList<>();

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public Optional<Restaurant> findByOwnerId(OwnerId ownerId) {
        return Optional.empty();
    }
}
