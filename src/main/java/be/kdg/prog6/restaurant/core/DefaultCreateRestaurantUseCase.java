package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.common.events.RestaurantCreatedEvent;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.Picture;
import be.kdg.prog6.restaurant.port.in.CreateRestaurantCommand;
import be.kdg.prog6.restaurant.port.in.CreateRestaurantUseCase;
import be.kdg.prog6.restaurant.port.out.LoadRestaurantPort;
import be.kdg.prog6.restaurant.port.out.PublishRestaurantEventPort;
import be.kdg.prog6.restaurant.port.out.UpdateRestaurantPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DefaultCreateRestaurantUseCase implements CreateRestaurantUseCase {
    private final Logger logger = LoggerFactory.getLogger(DefaultCreateRestaurantUseCase.class.getName());

    private final UpdateRestaurantPort updateRestaurantPort;
    private final PublishRestaurantEventPort publishRestaurantEventPort;
    private final LoadRestaurantPort loadRestaurantPort;

    public DefaultCreateRestaurantUseCase(UpdateRestaurantPort updateRestaurantPort, PublishRestaurantEventPort publishRestaurantEventPort, LoadRestaurantPort loadRestaurantPort){
        this.updateRestaurantPort = updateRestaurantPort;
        this.publishRestaurantEventPort = publishRestaurantEventPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(CreateRestaurantCommand command) {
        // some error should be returned that restaurant already exists
        OwnerId ownerId = command.ownerId();
        if (loadRestaurantPort.findByOwnerId(ownerId).isPresent()) {
            logger.info("Restaurant exists with owner id:" + ownerId);
            return loadRestaurantPort.findByOwnerId(ownerId).get();
        }

        Restaurant restaurant = new Restaurant(
                OwnerId.MICHAEL,
                command.name(),
                command.address(),
                command.emailAddress(),
                command.pictureList(),
                command.CUISINETYPE(),
                command.defaultPrepTime(),
                command.openingHours());

        // **add the domain event**
        restaurant.addDomainEvent(new RestaurantCreatedEvent(
                restaurant.getRestaurantId().id().toString(),
                restaurant.getOwnerId().id().toString(),
                restaurant.getOwnerId().name(),
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



        this.updateRestaurantPort.addRestaurant(restaurant);
        this.publishRestaurantEventPort.publishRestaurantCreated(restaurant);
        return restaurant;
    }
}
