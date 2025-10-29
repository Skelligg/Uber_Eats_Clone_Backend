package be.kdg.prog6.ordering.port.in.restaurantProjection;

public interface RestaurantsChangedProjector {
    void project(RestaurantAddedProjectionCommand projectionCommand);
}
