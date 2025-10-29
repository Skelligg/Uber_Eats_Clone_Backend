package be.kdg.prog6.ordering.core.restaurantProjection;

import be.kdg.prog6.common.vo.CUISINE_TYPE;
import be.kdg.prog6.common.vo.DAY;
import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import be.kdg.prog6.ordering.port.in.restaurantProjection.RestaurantAddedProjectionCommand;
import be.kdg.prog6.ordering.port.in.restaurantProjection.RestaurantsChangedProjector;
import be.kdg.prog6.ordering.port.out.restaurantProjection.UpdateRestaurantsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class RestaurantsChangedProjecterImpl implements RestaurantsChangedProjector {

    private final UpdateRestaurantsPort updateRestaurantsPort;

    public RestaurantsChangedProjecterImpl( UpdateRestaurantsPort updateRestaurantsPort) {
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
