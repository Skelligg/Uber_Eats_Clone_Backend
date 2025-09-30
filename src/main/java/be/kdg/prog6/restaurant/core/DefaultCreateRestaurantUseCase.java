package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.*;
import be.kdg.prog6.restaurant.port.in.CreateRestaurantCommand;
import be.kdg.prog6.restaurant.port.in.CreateRestaurantUseCase;
import be.kdg.prog6.restaurant.port.out.LoadRestaurantPort;
import be.kdg.prog6.restaurant.port.out.UpdateRestaurantPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.logging.Logger;

public class DefaultCreateRestaurantUseCase implements CreateRestaurantUseCase {
    private final Logger logger = Logger.getLogger(DefaultCreateRestaurantUseCase.class.getName());

    private final UpdateRestaurantPort updateRestaurantPort;
    private final LoadRestaurantPort loadRestaurantPort;

    public DefaultCreateRestaurantUseCase(UpdateRestaurantPort updateRestaurantPort, LoadRestaurantPort loadRestaurantPort){
        this.updateRestaurantPort = updateRestaurantPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(CreateRestaurantCommand command) {
        // implement search for restaurant by ownerId to see if owner already has restaurant
        // for now statically done
        OwnerId ownerId = command.ownerId();
        if (loadRestaurantPort.LoadBy(ownerId).isPresent()) {
            logger.info("Restaurant exists with owner id:" + ownerId);
            return loadRestaurantPort.LoadBy(ownerId).get();
        }

        return updateRestaurantPort.addRestaurant(new Restaurant(
                OwnerId.MICHAEL,
                "Michael's Pizza",
                new Address("Gijselsstraat","31","2160","Antwerp","Belgium"),
                new EmailAddress("michael@pizza.com"),
                CUISINE_TYPE.ITALIAN,
                new PrepTime(10,25),
                new OpeningHours(LocalTime.of(12,0,0),LocalTime.of(22,30,0))
        ));
    }
}
