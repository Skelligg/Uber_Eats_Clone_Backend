package be.kdg.prog6.ordering.port.in;

public interface RestaurantsChangedProjector {
    void project(RestaurantAddedProjectionCommand projectionCommand);
}
