package be.kdg.prog6.ordering.core;

import be.kdg.prog6.common.vo.CUISINE_TYPE;
import be.kdg.prog6.common.vo.DAY;
import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import be.kdg.prog6.ordering.port.in.restaurant.RestaurantAddedProjectionCommand;
import be.kdg.prog6.ordering.port.in.restaurant.RestaurantsChangedProjector;
import be.kdg.prog6.ordering.port.out.restaurant.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.restaurant.UpdateRestaurantsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
         updateRestaurantsPort.update( new RestaurantProjection(
                projectionCommand.restaurantId(),
                projectionCommand.name(),
                projectionCommand.street(),
                projectionCommand.number(),
                projectionCommand.postalCode(),
                projectionCommand.city(),
                projectionCommand.country(),
                projectionCommand.emailAddress(),
                projectionCommand.pictures(),
                CUISINE_TYPE.valueOf(projectionCommand.cuisineType()),
                projectionCommand.minPrepTime(),
                projectionCommand.maxPrepTime(),
                projectionCommand.openingTime(),
                projectionCommand.closingTime(),
                 projectionCommand.openDays().stream().map(DAY::valueOf).collect(Collectors.toList())
        ));
    }

}
