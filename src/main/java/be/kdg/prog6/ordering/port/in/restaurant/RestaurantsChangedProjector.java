package be.kdg.prog6.ordering.port.in.restaurant;

public interface RestaurantsChangedProjector {
    void project(RestaurantAddedProjectionCommand projectionCommand);
}
