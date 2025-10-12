package be.kdg.prog6.restaurant.adaptor.out.dish;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.port.out.PublishDishEventPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DishEventPublisher implements PublishDishEventPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public DishEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void updateDish(Dish dish) {
        dish.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
    }
}
