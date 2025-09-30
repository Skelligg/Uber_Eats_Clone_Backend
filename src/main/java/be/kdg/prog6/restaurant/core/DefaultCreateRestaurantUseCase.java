package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.*;
import be.kdg.prog6.restaurant.port.in.CreateRestaurantCommand;
import be.kdg.prog6.restaurant.port.in.CreateRestaurantUseCase;
import be.kdg.prog6.restaurant.port.out.UpdateRestaurantPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.logging.Logger;

public class DefaultCreateRestaurantUseCase implements CreateRestaurantUseCase {
    private final Logger logger = Logger.getLogger(DefaultCreateRestaurantUseCase.class.getName());

    private final UpdateRestaurantPort updateRestaurantPort;

    public DefaultCreateRestaurantUseCase(UpdateRestaurantPort updateRestaurantPort){
        this.updateRestaurantPort = updateRestaurantPort;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(CreateRestaurantCommand command) {



        Restaurant restaurant = updateRestaurantPort.addRestaurant(new Restaurant(
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
