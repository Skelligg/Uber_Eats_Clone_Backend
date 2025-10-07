package be.kdg.prog6.ordering.core;

import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import be.kdg.prog6.ordering.port.in.RestaurantAddedProjectionCommand;
import be.kdg.prog6.ordering.port.in.RestaurantsChangedProjector;
import be.kdg.prog6.ordering.port.out.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.UpdateRestaurantsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantsChangedProjecterImpl implements RestaurantsChangedProjector {

    private final LoadRestaurantsPort loadRestaurantsPort;
    private final UpdateRestaurantsPort updateRestaurantsPort;

    public RestaurantsChangedProjecterImpl(LoadRestaurantsPort loadRestaurantsPort, UpdateRestaurantsPort updateRestaurantsPort) {
        this.loadRestaurantsPort = loadRestaurantsPort;
        this.updateRestaurantsPort = updateRestaurantsPort;
    }

    @Override
    @Transactional
    public void project(RestaurantAddedProjectionCommand projectionCommand) {
        RestaurantProjection restaurantProjection = new RestaurantProjection(
                projectionCommand.restaurantId(),
                projectionCommand.ownerId(),
                projectionCommand.ownerName(),
                projectionCommand.name(),
                projectionCommand.street(),
                projectionCommand.number(),
                projectionCommand.postalCode(),
                projectionCommand.city(),
                projectionCommand.country(),
                projectionCommand.emailAddress(),
                projectionCommand.pictures(),
                projectionCommand.cuisineType(),
                projectionCommand.minPrepTime(),
                projectionCommand.maxPrepTime(),
                projectionCommand.openingTime(),
                projectionCommand.closingTime(),
                projectionCommand.openDays()
        );

        updateRestaurantsPort.update(restaurantProjection);
    }

}
