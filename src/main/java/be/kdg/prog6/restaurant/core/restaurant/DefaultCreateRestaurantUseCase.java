package be.kdg.prog6.restaurant.core.restaurant;

import be.kdg.prog6.common.events.restaurant.RestaurantCreatedEvent;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.Picture;
import be.kdg.prog6.restaurant.port.in.restaurant.CreateRestaurantCommand;
import be.kdg.prog6.restaurant.port.in.restaurant.CreateRestaurantUseCase;
import be.kdg.prog6.restaurant.port.out.restaurant.LoadRestaurantPort;
import be.kdg.prog6.restaurant.port.out.restaurant.UpdateRestaurantPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class DefaultCreateRestaurantUseCase implements CreateRestaurantUseCase {
    private final Logger logger = LoggerFactory.getLogger(DefaultCreateRestaurantUseCase.class.getName());

    private final LoadRestaurantPort loadRestaurantPort;
    private final List<UpdateRestaurantPort> updateRestaurantPorts;

    public DefaultCreateRestaurantUseCase(List<UpdateRestaurantPort> updateRestaurantPorts, LoadRestaurantPort loadRestaurantPort){
        this.updateRestaurantPorts = updateRestaurantPorts;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(CreateRestaurantCommand command) {
        OwnerId ownerId = command.ownerId();
        if (loadRestaurantPort.findByOwnerId(ownerId.id()).isPresent()) {
            logger.info("Restaurant exists with owner id:" + ownerId);
            return loadRestaurantPort.findByOwnerId(ownerId.id()).get();
        }

        Restaurant restaurant = new Restaurant(
                OwnerId.of(command.ownerId().id(),command.ownerId().name()),
                command.name(),
                command.address(),
                command.emailAddress(),
                command.pictureList(),
                command.CUISINETYPE(),
                command.defaultPrepTime(),
                command.openingHours());

        restaurant.addDomainEvent(new RestaurantCreatedEvent(
                restaurant.getRestaurantId().id().toString(),
                restaurant.getName(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().postalCode(),
                restaurant.getAddress().city(),
                restaurant.getAddress().country(),
                restaurant.getEmailAddress().emailAddress(),
                restaurant.getPictureList().stream().map(Picture::url).toList(),
                restaurant.getCuisineType().name(),
                restaurant.getDefaultPrepTime().minTime(),
                restaurant.getDefaultPrepTime().maxTime(),
                restaurant.getOpeningHours().openingTime(),
                restaurant.getOpeningHours().closingTime(),
                restaurant.getOpeningHours().openDays().stream()
                        .map(Enum::name)
                        .toList()
        ));

        this.updateRestaurantPorts.forEach(port -> port.updateRestaurant(restaurant)) ;
        return restaurant;
    }
}
